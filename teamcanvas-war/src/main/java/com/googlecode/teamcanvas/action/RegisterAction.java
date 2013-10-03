package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.service.UserService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterAction {
    private final Logger log = Logger.getLogger("RegisterAction");

    private User user;
    private String confirmPassword;
    private FacesContext facesContext;
    private UIComponent registerButton;

    @EJB
    private UserService userService;

    private final String INPUT_PASSWORD_VIEW_ID = "password";
    private final String INPUT_CONFIRM_PASSWORD_VIEW_ID = "confirmPassword";

    @PostConstruct
    public void setUp(){
        if(user == null){
            user = new User();
        }
        facesContext = FacesContext.getCurrentInstance();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public String register(){
        if(!isSuccessfullyRegistered()) {
            generateErrorMessage();
            return "register";
        }
        return "index.xhtml?faces-redirect=true";
    }

    public UIComponent getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(UIComponent registerButton) {
        this.registerButton = registerButton;
    }

    private void generateErrorMessage() {
        FacesMessage invalidUserMessage = new FacesMessage("User already exists");
        facesContext.addMessage(registerButton.getClientId(facesContext), invalidUserMessage);
    }

    private boolean isSuccessfullyRegistered() {
        boolean isSuccessful = false;
        try{
            isSuccessful = userService.saveUser(user);
        }catch (EJBException e){
            log.info(user.getEmail() + " already exists");
        }
        return isSuccessful;

    }

    public void validatePassword(ComponentSystemEvent event) {
        UIComponent components = event.getComponent();

        UIInput inputPassword = getInputComponent(components, INPUT_PASSWORD_VIEW_ID);
        UIInput inputConfirmPassword = getInputComponent(components, INPUT_CONFIRM_PASSWORD_VIEW_ID);

        if(!passwordMatchesWithConfirmPassword(inputPassword, inputConfirmPassword)) {
            renderErrorMessage(inputConfirmPassword);
        }
    }

    private void renderErrorMessage(UIInput errorCausingComponent) {
        FacesMessage errorMessage = new FacesMessage("Password must match confirm password");
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesContext.addMessage(errorCausingComponent.getClientId(), errorMessage);
        facesContext.renderResponse();
    }

    private boolean passwordMatchesWithConfirmPassword(UIInput inputPassword, UIInput inputConfirmPassword) {
        if(isEmptyInput(inputPassword, inputConfirmPassword)){
            String password = inputPassword.getLocalValue().toString();
            String confirmPassword = inputConfirmPassword.getLocalValue().toString();
            if(confirmPassword.equals(password)){
                return true;
            }
        }
        return  false;
    }

    private boolean isEmptyInput(UIInput inputPassword, UIInput inputConfirmPassword) {
        return (inputPassword.getLocalValue() != null
                && inputConfirmPassword.getLocalValue() != null);
    }

    private UIInput getInputComponent(UIComponent components, String componentName){
        UIInput inputComponent = (UIInput) components.findComponent(componentName);
        return  inputComponent;
    }
}
