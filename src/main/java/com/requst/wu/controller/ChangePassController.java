package com.requst.wu.controller;

import com.requst.wu.mail.HtmlRequestChangePass;

import com.requst.wu.mail.SendGmail;
import com.requst.wu.model.*;
import com.requst.wu.*;
import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.model.DicState;
import com.requst.wu.model.User;
import com.requst.wu.model.WuRequestChangePass;
import com.requst.wu.service.AcWuDictUserService;
import com.requst.wu.service.DicStateService;
import com.requst.wu.service.UserService;
import com.requst.wu.service.WuRequestChangePassService;
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
public class ChangePassController {

    private String subject = "TEST/АКОРДБАНК. Запит на зміну паролю по системі Western Union";

    final Logger logger = LoggerFactory.getLogger(ChangePassController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DicStateService dicStateService;

    @Autowired
    private AcWuDictUserService acWuDictUserService;

    @Autowired
    private WuRequestChangePassService wuRequestChangePassService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SendGmail sendGmail;

    // New request
    @RequestMapping(value = "/admin/changePass/newChangePass", method = RequestMethod.GET)
    public ModelAndView newRequestChangePayment() {

        logger.debug("************************************");
        logger.debug("Отправка запроса на изменение пароля");

        // Created a model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePass/newChangePass");
        // Created a new request
        WuRequestChangePass wuRequest = new WuRequestChangePass();
        // Got user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        logger.debug("Пользователь: [" + user.getEmail() + "] = [" + user.getLastName() + "]");
        wuRequest.setPrimaryUser(user);
        // Got user's parameters for WU processes
        AcWuDictUserTermDiv acWuDictUserTermDiv = acWuDictUserService.findByEmail(auth.getName());
        // If we do not have this user's data in database
        if (acWuDictUserTermDiv == null) {
            acWuDictUserTermDiv.setCodeTerminal("");
            acWuDictUserTermDiv.setTt(0);
            acWuDictUserTermDiv.setOperatorNo(0);
            acWuDictUserTermDiv.setOperatorFio(user.getLastName());
            acWuDictUserTermDiv.setEmail(user.getEmail());
        }
        wuRequest.setPrimaryWuDicUser(acWuDictUserTermDiv);
        modelAndView.addObject("wuRequest", wuRequest);
        modelAndView.addObject("acWuDictUserTermDiv", acWuDictUserTermDiv);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/changePass/newChangePass", method = RequestMethod.POST)
    public ModelAndView createNewRequestChangePayment(@Valid @ModelAttribute("wuRequest") WuRequestChangePass wuRequest, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        // Got date
        Date dateRequest = new Date(System.currentTimeMillis());
        // Got user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Got user's parameters for WU processes
        AcWuDictUserTermDiv acWuDictUser = acWuDictUserService.findByEmail(auth.getName());

        // If form haven't passed validation
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequest", wuRequest);
            modelAndView.addObject("acWuDictUserTermDiv", acWuDictUser);
            modelAndView.setViewName("admin/changePass/newChangePass");
        }
        // In case of successful validation, we generate a letter
        else {
            // User who logged in
            User user = userService.findUserByEmail(auth.getName());
            // User who created a request
            wuRequest.setPrimaryUser(user);
            // Date
            wuRequest.setDateRequest(dateRequest);
            // User parameters from WU database
            wuRequest.setPrimaryWuDicUser(acWuDictUser);
            // Checking request status - "1 - saved"
            DicState dicState = dicStateService.findByStateId(1);
            wuRequest.setPrimaryState(dicState);

            wuRequestChangePassService.saveWuRequest(wuRequest);
            // Text of the latter in HTML format
            String textEmail = new HtmlRequestChangePass().creatHtmlBodyMail(wuRequest, wuRequest.getPrimaryWuDicUser());

            // Sending the letter to the address
            String resultSending = sendGmail.sendEmailWithAttachment(javaMailSender, subject, textEmail, null);
            modelAndView.addObject("typeMessage", resultSending);
            modelAndView.setViewName("admin/Message");
        }
        return modelAndView;
    }

    // Viewing the request on changing payment details
    @RequestMapping(value = "/admin/changePass/viewChangePass/{id}", method = RequestMethod.GET)
    public ModelAndView viewRequestPaymentUpdate(@PathVariable Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePass/viewChangePass");


        WuRequestChangePass wuRequest = wuRequestChangePassService.findByWuId(id);

        modelAndView.addObject("wuRequest", wuRequest);
        modelAndView.addObject("acWuDictUserTermDiv", wuRequest.getPrimaryWuDicUser());
        return modelAndView;
    }


    @PostMapping(path = "/admin/deleteRequestChangePass/{id}")
    public String deleteBook(@PathVariable Integer id) {
        WuRequestChangePass wuRequest = wuRequestChangePassService.findByWuId(id);
        wuRequestChangePassService.delete(wuRequest);

        return "redirect:/admin/HistoryRequest/requestChangePass";
    }


    @RequestMapping(value = "/admin/changePass/updateChangePass/{id}", method = RequestMethod.GET)
    public ModelAndView updateRequestPaymentUpdate(@PathVariable Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/changePass/updateChangePass");

        WuRequestChangePass wuRequest = wuRequestChangePassService.findByWuId(id);

        modelAndView.addObject("acWuDictUserTermDiv", wuRequest.getPrimaryWuDicUser());
        modelAndView.addObject("wuRequest", wuRequest);
        return modelAndView;
    }


    @RequestMapping(value = "/admin/changeChangePass/updateChangePass/{id}", method = RequestMethod.POST)
    public ModelAndView updateNewRequestChangePayment(@Valid @ModelAttribute("wuRequest") WuRequestChangePass wuRequestChangePass, @PathVariable Integer id, BindingResult bindingResult) throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AcWuDictUserTermDiv acWuDictUser = acWuDictUserService.findByEmail(auth.getName());
        WuRequestChangePass wuRequest = wuRequestChangePassService.findByWuId(id);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("wuRequest", wuRequest);
            modelAndView.setViewName("admin/changePass/updateChangePass");
        } else {
            Date dateRequest = new Date(System.currentTimeMillis());
            wuRequest.setDateRequest(dateRequest);
            wuRequest.setPrimaryWuDicUser(acWuDictUser);
            DicState dicState = dicStateService.findByStateId(2);
            wuRequest.setPrimaryState(dicState);
            String textEmail = new HtmlRequestChangePass().creatHtmlBodyMail(wuRequest, wuRequest.getPrimaryWuDicUser());
            modelAndView.setViewName("admin/Message");

        }
        return modelAndView;
    }
}
