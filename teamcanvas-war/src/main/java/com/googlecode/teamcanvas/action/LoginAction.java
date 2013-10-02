package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginAction {
    @EJB
    private UserService userService;

    //@PostConstruct
    public void setUp(){
        User user = new User();
        user.setEmail("rony.gomes89@gmail.com");
        user.setFirstName("Manuel");
        user.setLastName("Gomes");
        user.setHashedPassword("12345");

        userService.saveUser(user);

    }

    public String getTest(){
        setUp();
        return "this is form LoginAction";
    }
}
