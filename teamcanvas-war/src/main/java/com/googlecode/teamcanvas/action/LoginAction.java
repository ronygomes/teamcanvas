package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.UserService;



import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Named
@RequestScoped
public class LoginAction {
    private final Logger log = Logger.getLogger("LoginAction");

    private String userEmail;
    private String userPassword;
    private boolean rememberMe;
    private UIComponent submitButton;
    private FacesContext facesContext;
    private HttpSession httpSession;

    @EJB
    private UserService userService;

    public static String LOGIN_USER_SESSION_KEY = "loggedInUser";


    @PostConstruct
    public void setUp(){
        facesContext = FacesContext.getCurrentInstance();
        httpSession = (HttpSession)facesContext.getExternalContext().getSession(true);
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

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public UIComponent getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(UIComponent submitButton) {
        this.submitButton = submitButton;
    }


    public String login(){
        User user = userService.checkAuthenticity(userEmail, userPassword);
        String outcome = "index";

        if(isUserFound(user)){
            storeUserInSession(user);
            outcome = "home";
        }else{
            showInvalidUserMessage();
        }

        return outcome;
    }

    private void showInvalidUserMessage() {
        FacesMessage invalidUserMessage = new FacesMessage("Invalid username/password");
        facesContext.addMessage(submitButton.getClientId(facesContext), invalidUserMessage);
    }

    private void storeUserInSession(User authenticatedUser) {
        httpSession.setAttribute(LOGIN_USER_SESSION_KEY, authenticatedUser);
    }

    private boolean isUserFound(User user){
        return user != null;
    }


}
