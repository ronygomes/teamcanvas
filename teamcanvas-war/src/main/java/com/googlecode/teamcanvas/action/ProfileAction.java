package com.googlecode.teamcanvas.action;

import com.googlecode.teamcanvas.domain.User;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named
@RequestScoped
public class ProfileAction extends AppUtilTemplate{

    private Logger log = Logger.getLogger(ProfileAction.class);

    private User user;
    private String confirmPassword;
    private UploadedFile file;

    private static final String INPUT_PASSWORD_VIEW_ID = "password";
    private static final String INPUT_CONFIRM_PASSWORD_VIEW_ID = "confirmPassword";


    @PostConstruct
    public void setUp(){
        initUtilParams();
        user = getLoggedInUser();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
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
        getFacesContext().addMessage(errorCausingComponent.getClientId(), errorMessage);
        getFacesContext().renderResponse();
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
        return (UIInput) components.findComponent(componentName);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void editProfile(){
        log.info("File Name" + file.getFileName());

    }
}
