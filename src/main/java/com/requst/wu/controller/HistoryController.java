package com.requst.wu.controller;


import com.requst.wu.model.WuRequestChangePass;
import com.requst.wu.model.WuRequestLimit;
import com.requst.wu.model.WuRequestPayment;
import com.requst.wu.service.*;
import com.requst.wu.service.UserService;
import com.requst.wu.service.WuRequestChangePassService;
import com.requst.wu.service.WuRequestLimitService;
import com.requst.wu.service.WuRequestPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HistoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private WuRequestLimitService wuRequestLimitService;

    @Autowired
    private WuRequestChangePassService wuRequestChangePassService;

    @Autowired
    private WuRequestPaymentService wuRequestPaymentService;

    private String sortDateMethod = "ASC";

    @RequestMapping(value = "/operator/historyAllRequestLimit", method = RequestMethod.GET)
    public ModelAndView history(@RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "id") String sortBy,
                                @RequestParam(required = false, defaultValue = "ask") String order,
                                @RequestParam(required = false, defaultValue = "") String term, // запрос на поиск
                                @RequestParam(required = false, defaultValue = "0") int afterYear, // минимальный год выхода книги в печать
                                @RequestParam(required = false, defaultValue = "1") int ready, Model uiModel) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("operator/historyAllRequestLimit");

        Integer pageNumber = (page > 0) ? page - 1 : 0;

        Pageable pageRequest = new PageRequest(pageNumber, 10, Sort.Direction.DESC, "dateRequest");
        Page<WuRequestLimit> listWuRequest = wuRequestLimitService.findByPrimaryState(ready,pageRequest);
        

        modelAndView.addObject("listWuRequest", listWuRequest);
        modelAndView.addObject("current", pageNumber);
        modelAndView.addObject("term", term);
        modelAndView.addObject("year", afterYear);
        modelAndView.addObject("ready", ready);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/HistoryRequest/{typeRequest}", method = RequestMethod.GET)
    public ModelAndView HistoryRequest(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(required = false, defaultValue = "id") String sortBy,
                                              @RequestParam(required = false, defaultValue = "desc") String order,
                                              @RequestParam(required = false, defaultValue = "") String term, // запрос на поиск
                                              @PathVariable String typeRequest, //Тип заявки (лимит, изменение реквизитов..)
                                              @RequestParam(required = false, defaultValue = "0") int ready) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/HistoryRequest");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Integer pageNumber = (page > 0) ? page - 1 : 0;

        Sort.Direction direction;
        if (order.equals("desc")) direction=Sort.Direction.DESC;
        else direction=Sort.Direction.ASC;

        Pageable pageRequest =new PageRequest(pageNumber,10,direction,sortBy);

        switch (typeRequest) {
            case "requestPayment" : {
                Page<WuRequestPayment> listWuRequest;
                // Filter by the parameter order status 0 - All orders, 1- saved, 2- weather
                  if (ready==0) {
                   listWuRequest = wuRequestPaymentService.findBySearchParams(term, auth.getName(), pageRequest);
                  } else {
                   listWuRequest = wuRequestPaymentService.findBySearchParamsAndState(term, auth.getName(), ready, pageRequest);
                  }
                  modelAndView.addObject("listWuRequest", listWuRequest);
                };

                break;
            case "requestLimit" : {
                Page<WuRequestLimit> listWuRequest;
                  if (ready==0) {
                   listWuRequest = wuRequestLimitService.findBySearchParams(term, auth.getName(), pageRequest);
                  } else {
                   listWuRequest = wuRequestLimitService.findBySearchParamsAndState(term, auth.getName(), ready, pageRequest);
                  }
                 modelAndView.addObject("listWuRequest", listWuRequest);
                 };
                  break;
            case "requestChangePass" : {
                Page<WuRequestChangePass> listWuRequest;
                if (ready==0) {
                    listWuRequest = wuRequestChangePassService.findBySearchParams(auth.getName(), pageRequest);
                } else {
                    listWuRequest = wuRequestChangePassService.findBySearchParamsAndState(auth.getName(), ready, pageRequest);
                }
                modelAndView.addObject("listWuRequest", listWuRequest);
                };break;
            default: {};break;
            }

            modelAndView.addObject("current", pageNumber);
            modelAndView.addObject("term", term);
            modelAndView.addObject("typeRequest", typeRequest);
            modelAndView.addObject("ready", ready);

        return modelAndView;
    }


    @GetMapping("/admin/HistoryRequest/{typeRequest}/{order}")
    public ModelAndView sortChoose(@RequestParam(required = false, defaultValue = "1") Integer page,
                                   @RequestParam(required = false, defaultValue = "dateRequest") String sortBy,
                                   @RequestParam(required = false, defaultValue = "") String term, // запрос на поиск
                                   @PathVariable String typeRequest, //Тип заявки (лимит, изменение реквизитов..)
                                   @PathVariable String order, //Тип заявки (лимит, изменение реквизитов..)
                                   @RequestParam(required = false, defaultValue = "0") int ready) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/HistoryRequest");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Integer pageNumber = (page > 0) ? page - 1 : 0;


        Sort.Direction direction;
        if (order.equals("desc")) direction=Sort.Direction.DESC;
        else direction=Sort.Direction.ASC;

        Pageable pageRequest =new PageRequest(pageNumber,10,direction,sortBy);

        switch (typeRequest) {
            case "requestPayment" : {
                Page<WuRequestPayment> listWuRequest;
                if (ready==0) {
                    listWuRequest = wuRequestPaymentService.findBySearchParams(term, auth.getName(), pageRequest);
                } else {
                    listWuRequest = wuRequestPaymentService.findBySearchParamsAndState(term, auth.getName(), ready, pageRequest);
                }
                modelAndView.addObject("listWuRequest", listWuRequest);
            };

            break;
            case "requestLimit" : {
                Page<WuRequestLimit> listWuRequest;
                if (ready==0) {
                    listWuRequest = wuRequestLimitService.findBySearchParams(term, auth.getName(), pageRequest);
                } else {
                    listWuRequest = wuRequestLimitService.findBySearchParamsAndState(term, auth.getName(), ready, pageRequest);
                }
                modelAndView.addObject("listWuRequest", listWuRequest);
            };
            break;
            case "requestChangePass" : {
                Page<WuRequestChangePass> listWuRequest;
                if (ready==0) {
                    listWuRequest = wuRequestChangePassService.findBySearchParams(auth.getName(), pageRequest);
                } else {
                    listWuRequest = wuRequestChangePassService.findBySearchParamsAndState(auth.getName(), ready, pageRequest);
                }
                modelAndView.addObject("listWuRequest", listWuRequest);
            };break;
            default: {};break;
        }

        modelAndView.addObject("current", pageNumber);
        modelAndView.addObject("term", term);
        modelAndView.addObject("typeRequest", typeRequest);
        modelAndView.addObject("ready", ready);

        return modelAndView;

    }




}
