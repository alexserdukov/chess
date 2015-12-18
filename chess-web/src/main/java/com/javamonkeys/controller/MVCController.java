package com.javamonkeys.controller;

import com.javamonkeys.controller.user.IUserController;
import com.javamonkeys.entity.user.User;
import com.javamonkeys.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MVCController {

    @Autowired
    IUserService userService;

    @Autowired
    IUserController userController;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        User currentUser = getCurrentSessionUser();
        model.addObject("userName", getUserDescription(currentUser));
        model.addObject("userId", getUserId(currentUser));
        model.setViewName("index");
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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView model = new ModelAndView();
        model.setViewName("register");
        return model;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView model = new ModelAndView();
        User currentUser = getCurrentSessionUser();
        model.addObject("userName", getUserDescription(currentUser));
        model.addObject("userId", getUserId(currentUser));
        model.setViewName("profile");
        return model;
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ModelAndView statistics() {
        ModelAndView model = new ModelAndView();
        User currentUser = getCurrentSessionUser();
        model.addObject("userName", getUserDescription(currentUser));
        model.addObject("userId", getUserId(currentUser));
        model.setViewName("statistics");
        return model;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView model = new ModelAndView();
        User currentUser = getCurrentSessionUser();
        model.addObject("userName", getUserDescription(currentUser));
        model.addObject("userId", getUserId(currentUser));
        model.setViewName("about");
        return model;
    }

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

    private User getCurrentSessionUser() {
        return userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private String getUserDescription(User user) {
        if (user == null) {
            return "<null>";
        } else {
            if (user.getName().isEmpty())
                return user.getEmail();
            else
                return user.getName() + " (" + user.getEmail() + ")";
        }
    }

    private Integer getUserId(User user) {
        return (user == null) ? 0 : user.getId();
    }
}
