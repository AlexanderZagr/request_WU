package com.requst.wu.controller;


import com.requst.wu.mail.HtmlRequestPayment;
import com.requst.wu.mail.SendGmail;
import com.requst.wu.model.*;
import com.requst.wu.*;

import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.model.DicState;
import com.requst.wu.model.User;
import com.requst.wu.model.WuRequestPayment;
import com.requst.wu.service.AcWuDictUserService;
import com.requst.wu.service.DicStateService;
import com.requst.wu.service.UserService;
import com.requst.wu.service.WuRequestPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;


@Controller
public class ChangePaymentController {
    private String to="auzagraevskiy@gmail.com" ;
    private String subject ="TEST/АККОРДБАНК. Запит на зміну реквізитів переказу";

    final Logger logger = LoggerFactory.getLogger(ChangePaymentController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DicStateService dicStateService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private WuRequestPaymentService wuRequestPaymentService;

    @Autowired
    private AcWuDictUserService acWuDictUserService;

    @Autowired
    private SendGmail sendGmail;


    @RequestMapping(value="/admin/changePayment/newChangePayment", method = RequestMethod.GET)
    public ModelAndView newRequestChangePayment(){
        logger.debug("************************************");
        logger.debug("Отправка запроса на изменение реквизитов платежа");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePayment/newChangePayment");

        WuRequestPayment wuRequestPayment=new WuRequestPayment();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        logger.debug("Пользователь: ["+user.getEmail()+"] = ["+user.getLastName()+"]");
        wuRequestPayment.setPrimaryUserPayment(user);
        AcWuDictUserTermDiv acWuDictUserTermDiv=acWuDictUserService.findByEmail(auth.getName());

        if (acWuDictUserTermDiv==null) {
            acWuDictUserTermDiv.setCodeTerminal("");
            acWuDictUserTermDiv.setTt(0);
            acWuDictUserTermDiv.setOperatorNo(0);
            acWuDictUserTermDiv.setOperatorFio(user.getLastName());
            acWuDictUserTermDiv.setEmail(user.getEmail());
            }
        wuRequestPayment.setPrimaryWuDicUserPayment(acWuDictUserTermDiv);
        modelAndView.addObject("wuRequestPayment",wuRequestPayment);
        modelAndView.addObject("acWuDictUserTermDiv",acWuDictUserTermDiv);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/changePayment/newChangePayment", method = RequestMethod.POST)
    public ModelAndView createNewRequestChangePayment(@Valid @ModelAttribute("wuRequestPayment") WuRequestPayment wuRequestPayment, @RequestParam MultipartFile file, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        Date dateRequest  = new Date(System.currentTimeMillis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AcWuDictUserTermDiv acWuDictUser=acWuDictUserService.findByEmail(auth.getName());

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequestPayment",wuRequestPayment);
            modelAndView.setViewName("admin/changePayment/newChangePayment");
        }

        else {
            User user = userService.findUserByEmail(auth.getName());
            wuRequestPayment.setPrimaryUserPayment(user);
            wuRequestPayment.setDateRequest(dateRequest);
            wuRequestPayment.setPrimaryWuDicUserPayment(acWuDictUser);
            // Checking request status - "1 - saved"
            DicState dicState=dicStateService.findByStateId(1);
            wuRequestPayment.setPrimaryStatePayment(dicState);
            // Downloaded the file
            wuRequestPayment = wuRequestPaymentService.uploadFileData(wuRequestPayment, file);
            // Saved tge request
            wuRequestPaymentService.saveWuRequest(wuRequestPayment);
            // Text of the latter in HTML format
            String textEmail=new HtmlRequestPayment().creatHtmlBodyMail(wuRequestPayment, wuRequestPayment.getPrimaryWuDicUserPayment());
            // Sending the letter to the address
            String resultSending= sendGmail.sendEmailWithAttachment(javaMailSender,subject,textEmail,file.getOriginalFilename());
            modelAndView.addObject("typeMessage",resultSending);
            modelAndView.setViewName("admin/Message");
        }
        return modelAndView;
    }



    @RequestMapping(value="/admin/test", method = RequestMethod.GET)
    public ModelAndView newRequestChangePaymentTest(){
        logger.debug("************************************");
        logger.debug("Отправка запроса на изменение реквизитов платежа");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/test");

        WuRequestPayment wuRequestPayment=new WuRequestPayment();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        logger.debug("Пользователь: ["+user.getEmail()+"] = ["+user.getLastName()+"]");
        wuRequestPayment.setPrimaryUserPayment(user);

        AcWuDictUserTermDiv acWuDictUserTermDiv=acWuDictUserService.findByEmail(auth.getName());

        if (acWuDictUserTermDiv==null) {
            acWuDictUserTermDiv.setCodeTerminal("");
            acWuDictUserTermDiv.setTt(0);
            acWuDictUserTermDiv.setOperatorNo(0);
            acWuDictUserTermDiv.setOperatorFio(user.getLastName());
            acWuDictUserTermDiv.setEmail(user.getEmail());
        }
        wuRequestPayment.setPrimaryWuDicUserPayment(acWuDictUserTermDiv);
        modelAndView.addObject("wuRequestPayment",wuRequestPayment);
        modelAndView.addObject("acWuDictUserTermDiv",acWuDictUserTermDiv);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/test", method = RequestMethod.POST)
    public ModelAndView createNewRequestChangePaymentTest(@Valid @ModelAttribute("wuRequestPayment") WuRequestPayment wuRequestPayment, @RequestParam MultipartFile file, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        Date dateRequest  = new Date(System.currentTimeMillis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AcWuDictUserTermDiv acWuDictUser=acWuDictUserService.findByEmail(auth.getName());


        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequestPayment",wuRequestPayment);
            modelAndView.setViewName("admin/test");
        }
        else {
            User user = userService.findUserByEmail(auth.getName());
            wuRequestPayment.setPrimaryUserPayment(user);
            wuRequestPayment.setDateRequest(dateRequest);
            wuRequestPayment.setPrimaryWuDicUserPayment(acWuDictUser);
            // Checking request status - "1 - saved"
            DicState dicState=dicStateService.findByStateId(1);
            wuRequestPayment.setPrimaryStatePayment(dicState);
            // Downloaded the file
            wuRequestPayment = wuRequestPaymentService.uploadFileData(wuRequestPayment, file);
            // Saved tge request
            wuRequestPaymentService.saveWuRequest(wuRequestPayment);
            // Text of the latter in HTML format
            String textEmail=new HtmlRequestPayment().creatHtmlBodyMail(wuRequestPayment, wuRequestPayment.getPrimaryWuDicUserPayment());
            // Sending the letter to the address
            String resultSending= sendGmail.sendEmailWithAttachment(javaMailSender,subject,textEmail,file.getOriginalFilename());
            modelAndView.addObject("typeMessage",resultSending);   modelAndView.setViewName("admin/Message");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/changePayment/viewChangePayment/{id}", method = RequestMethod.GET)
    public ModelAndView viewRequestPaymentUpdate(@PathVariable Integer id){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePayment/viewChangePayment");


        WuRequestPayment wuRequestPayment=wuRequestPaymentService.findByWuId(id);

        modelAndView.addObject("wuRequestPayment",wuRequestPayment);
        modelAndView.addObject("acWuDictUserTermDiv",wuRequestPayment.getPrimaryWuDicUserPayment());
        return modelAndView;
    }


    @PostMapping(path = "/admin/deleteRequestChangePayment/{id}")
    public String deleteBook(@PathVariable Integer id){
        WuRequestPayment wuRequestPayment = wuRequestPaymentService.findByWuId(id);
        wuRequestPaymentService.delete(wuRequestPayment);

        return "redirect:/admin/HistoryRequest/requestPayment";
    }



    @RequestMapping(value="/admin/changePayment/updateChangePayment/{id}", method = RequestMethod.GET)
    public ModelAndView updateRequestPaymentUpdate(@PathVariable Integer id){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePayment/updateChangePayment");

        WuRequestPayment wuRequestPayment=wuRequestPaymentService.findByWuId(id);

        modelAndView.addObject("acWuDictUserTermDiv",wuRequestPayment.getPrimaryWuDicUserPayment());
        modelAndView.addObject("wuRequestPayment",wuRequestPayment);
        return modelAndView;
    }

    @RequestMapping(value="/admin/changePayment/updateChangePayment/{id}", method = RequestMethod.POST)
    public ModelAndView updateNewRequestChangePayment(@Valid @ModelAttribute("wuRequestPayment") WuRequestPayment wuRequestPayment,@PathVariable Integer id, @RequestParam MultipartFile file, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        Date dateRequest  = new Date(System.currentTimeMillis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AcWuDictUserTermDiv acWuDictUser=acWuDictUserService.findByEmail("belinskyi@accordbank.com.ua");

        WuRequestPayment wuRequest=wuRequestPaymentService.findByWuId(id);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequestPayment",wuRequestPayment);
            modelAndView.setViewName("admin/changePayment/updateChangePayment");
        }

        else {
            wuRequest.setDateRequest(dateRequest);
            wuRequest.setComent(wuRequestPayment.getComent());
            wuRequestPayment.setPrimaryWuDicUserPayment(acWuDictUser);
            // Checked request status - "2 - changed"
            DicState dicState=dicStateService.findByStateId(2);
            wuRequest.setPrimaryStatePayment(dicState);
            wuRequestPaymentService.update(wuRequest,id);
            modelAndView.setViewName("admin/Message");

        }
        return modelAndView;
    }


    @GetMapping(path = "admin/processRequestChangePayment/{id}/image")
    @ResponseBody
    public ResponseEntity<byte[]> getImageData(@PathVariable Integer id){

        byte[] imageData = wuRequestPaymentService.findByWuId(id).getImageData();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .header(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue())
                .body(imageData);
    }


}
