package com.javamonkeys.controller;

import com.javamonkeys.controller.user.IUserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class MVCController {

    @Inject
    IUserController userService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView game() {
        ModelAndView model = new ModelAndView();
        model.setViewName("game");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView model = new ModelAndView();
        model.setViewName("registration");
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView register(@RequestHeader(value="Authorization") String authorization) {
        ModelAndView model = new ModelAndView();
        ResponseEntity responseEntity = userService.createUser(authorization);
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            model.addObject("msg", "You have been successfully registered! Please, login!");
            model.setViewName("login");
        } else {
            model.addObject("error", "Incorrect user credentials or user with this email already exists!");
            model.setViewName("registration");
        }
        return model;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView model = new ModelAndView();
        model.setViewName("profile");
        return model;
    }

    @RequestMapping(value = "/mygames", method = RequestMethod.GET)
    public ModelAndView mygames() {
        ModelAndView model = new ModelAndView();
        model.setViewName("mygames");
        return model;
    }

    @RequestMapping(value = "/boards", method = RequestMethod.GET)
    public ModelAndView boards() {
        ModelAndView model = new ModelAndView();
        model.setViewName("boards");
        return model;
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statistics() {
        ModelAndView model = new ModelAndView();
        model.setViewName("statistics");
        return model;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView model = new ModelAndView();
        model.setViewName("about");
        return model;
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);
            model.addObject("username", userDetail.getUsername());
        }
        model.setViewName("403");
        return model;
    }

}
