package com.requst.wu.controller;

import javax.validation.Valid;

import com.requst.wu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.requst.wu.service.UserServiceImpl;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class LoginController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userServiceImpl.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userServiceImpl.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}



	@RequestMapping(value="/home/home", method = RequestMethod.GET)
	public ModelAndView home(){

        ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String userRole = "";

		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<SimpleGrantedAuthority> iterator =  authorities.iterator();

        while (iterator.hasNext()) {
            SimpleGrantedAuthority item = iterator.next();
            userRole=item.getAuthority();
        }

        User user = userServiceImpl.findUserByEmail(auth.getName());
		modelAndView.addObject("enterMessage","Ви увійшли в систему IDOC : ");
                modelAndView.addObject("userName", user.getName()+" "+user.getLastName());
                modelAndView.addObject("userEmail" , user.getEmail());
                modelAndView.addObject("userRole" , userRole);
		modelAndView.setViewName("home/home");
		return modelAndView;
	}



	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

}
