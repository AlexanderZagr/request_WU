package com.requst.wu.controller;

import com.requst.wu.mail.HtmlRequestLimit;
import com.requst.wu.mail.SendGmail;
import com.requst.wu.model.*;
import com.requst.wu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;


@Controller
public class ChangeLimitController {

    private String subject ="TEST/АККОРДБАНК. Запит на збільшення лімітів на відправку переказів по Western Union";

    final Logger logger = LoggerFactory.getLogger(ChangePaymentController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private DicStateService dicStateService;

    @Autowired
    private RequestStateService requestStateService;

    @Autowired
    private WuRequestLimitService wuRequestLimitService;

    @Autowired
    private AcWuDictUserService acWuDictUserService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SendGmail sendGmail;


    // New request
    @RequestMapping(value="/admin/changeLimit/newChangeLimit", method = RequestMethod.GET)
    public ModelAndView newRequestChangePayment(){

        logger.debug("************************************");
        logger.debug("Отправка запроса на изменение лимита");
        AcWuDictUserTermDiv acWuDictUserTermDiv;
        // Created a model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changeLimit/newChangeLimit");
        // Created a new request
        WuRequestLimit wuRequestLimit=new WuRequestLimit();
        // Got user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        logger.debug("Пользователь: ["+user.getEmail()+"] = ["+user.getLastName()+"]");
        wuRequestLimit.setPrimaryUser(user);
        // Got user's parameters for WU processes
        acWuDictUserTermDiv=acWuDictUserService.findByEmail(auth.getName());
        // If we do not have this user's data in database
        if (acWuDictUserTermDiv==null) {
            acWuDictUserTermDiv=new  AcWuDictUserTermDiv();
            acWuDictUserTermDiv.setCodeTerminal("немає данних");
            acWuDictUserTermDiv.setTt(0);
            acWuDictUserTermDiv.setOperatorNo(0);
            acWuDictUserTermDiv.setOperatorFio(user.getLastName());
            acWuDictUserTermDiv.setEmail(user.getEmail());
        }
        wuRequestLimit.setPrimaryWuDicUser(acWuDictUserTermDiv);
        modelAndView.addObject("wuRequestLimit",wuRequestLimit);
        modelAndView.addObject("acWuDictUserTermDiv",acWuDictUserTermDiv);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/changeLimit/newChangeLimit", method = RequestMethod.POST)
    public ModelAndView createNewRequestChangePayment(@Valid @ModelAttribute("wuRequestLimit") WuRequestLimit wuRequestLimit, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        AcWuDictUserTermDiv acWuDictUser;
        // Got date
        Date dateRequest  = new Date(System.currentTimeMillis());
        // Got user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Got user's parameters for WU processes
         acWuDictUser=acWuDictUserService.findByEmail(auth.getName());
        // If we do not have this user's data in database
        if (acWuDictUser==null) {
            acWuDictUser=new  AcWuDictUserTermDiv();
            acWuDictUser.setCodeTerminal("немає данних");
            acWuDictUser.setTt(0);
            acWuDictUser.setOperatorNo(0);

        }

        // If form haven't passed validation
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequestLimit",wuRequestLimit);
            modelAndView.addObject("acWuDictUserTermDiv",acWuDictUser);
            modelAndView.setViewName("admin/changeLimit/newChangeLimit");
        }
        // If validation was successful, forming the message
        else {
            // User who logged in
            User user = userService.findUserByEmail(auth.getName());
            // User who created a request
            wuRequestLimit.setPrimaryUser(user);
            // Date
            wuRequestLimit.setDateRequest(dateRequest);
            // User parameters from WU database
            wuRequestLimit.setPrimaryWuDicUser(acWuDictUser);
            // Checking request status - "1 - saved"
            DicState dicState=dicStateService.findByStateId(1);
            wuRequestLimit.setPrimaryState(dicState);

            // Checked request status - "1 - saved"
            RequestState requestState1= requestStateService.findByWuStateId(1);
            RequestState requestState2= requestStateService.findByWuStateId(2);

            wuRequestLimit.getRequestStates().add(requestState1);
            wuRequestLimit.getRequestStates().add(requestState2);

            wuRequestLimitService.saveWuRequest(wuRequestLimit);
            // Text of the latter in HTML format
            String textEmail=new HtmlRequestLimit().creatHtmlBodyMail(wuRequestLimit, wuRequestLimit.getPrimaryWuDicUser());
            // Sending the letter to the address
            String resultSending= sendGmail.sendEmailWithAttachment(javaMailSender,subject,textEmail,null);
            modelAndView.addObject("typeMessage",resultSending);
            modelAndView.setViewName("admin/Message");
        }
        return modelAndView;
    }

    // Viewing the request on changing payment details
    @RequestMapping(value="/admin/changeLimit/viewChangeLimit/{id}", method = RequestMethod.GET)
    public ModelAndView viewRequestPaymentUpdate(@PathVariable Integer id){
        // Created a model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changeLimit/viewChangeLimit");

        // Found a request according to it's id
        WuRequestLimit wuRequestLimit=wuRequestLimitService.findByWuId(id);

        modelAndView.addObject("wuRequestLimit",wuRequestLimit);
        modelAndView.addObject("acWuDictUserTermDiv",wuRequestLimit.getPrimaryWuDicUser());
        return modelAndView;
    }


    @PostMapping(path = "/admin/deleteRequestChangeLimit/{id}")
    public String deleteBook(@PathVariable Integer id){
        WuRequestLimit wuRequestLimit = wuRequestLimitService.findByWuId(id);
        wuRequestLimitService.delete(wuRequestLimit);

        return "redirect:/admin/HistoryRequest/requestLimit";
    }


    // Request to change payment details
    @RequestMapping(value="/admin/changeLimit/updateChangeLimit/{id}", method = RequestMethod.GET)
    public ModelAndView updateRequestPaymentUpdate(@PathVariable Integer id){
        // Created a model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changeLimit/updateChangeLimit");

        // Found a request according to it's id
        WuRequestLimit wuRequestLimit=wuRequestLimitService.findByWuId(id);

        modelAndView.addObject("acWuDictUserTermDiv",wuRequestLimit.getPrimaryWuDicUser());
        modelAndView.addObject("wuRequestLimit",wuRequestLimit);
        return modelAndView;
    }


    // Requests to change payment audits
    @RequestMapping(value="/admin/changeLimit/updateChangeLimit/{id}", method = RequestMethod.POST)
    public ModelAndView updateNewRequestChangePayment(@Valid @ModelAttribute("wuRequestLimit") WuRequestLimit wuRequestLimit,@PathVariable Integer id, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        // Got a user to write to the log, who changed the request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Got user's parameters for WU processes
        AcWuDictUserTermDiv acWuDictUser=acWuDictUserService.findByEmail(auth.getName());

        // Found a request according to it's id
        WuRequestLimit wuRequest=wuRequestLimitService.findByWuId(id);

        // If form haven't passed validation
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequestLimit",wuRequestLimit);
            modelAndView.setViewName("admin/changeLimit/updateChangeLimit");
        }
        // In case of successful validation, we generate a letter
        else {

            // Got date
            Date dateRequest  = new Date(System.currentTimeMillis());
            wuRequest.setDateRequest(dateRequest);
            wuRequest.setComent(wuRequestLimit.getComent());
            // User parameters
            wuRequestLimit.setPrimaryWuDicUser(acWuDictUser);
            // Checked request status - "2 - changed"
            DicState dicState=dicStateService.findByStateId(2);
            wuRequest.setPrimaryState(dicState);
            // Saving the request
            wuRequestLimitService.update(wuRequest,id);
            // Sending a letter to the address
            modelAndView.setViewName("admin/Message");

        }
        return modelAndView;
    }

}
