package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginAction {
    private String userEmail;
    private String userPassword;
    private boolean rememberMe;

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
        //setUp();
        return "this is form LoginAction";
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
