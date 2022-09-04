package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.UserService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Named
@RequestScoped
public class ProfileAction extends AppUtilTemplate {

    private Logger log = Logger.getLogger(ProfileAction.class);

    private User user;
    private String confirmPassword;
    private UploadedFile file;
    private UIComponent fileUploadComponent;

    @EJB
    private UserService userService;

    private static final String INPUT_PASSWORD_VIEW_ID = "password";
    private static final String INPUT_CONFIRM_PASSWORD_VIEW_ID = "confirmPassword";


    @PostConstruct
    public void setUp() {
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

        if (!passwordMatchesWithConfirmPassword(inputPassword, inputConfirmPassword)) {
            renderErrorMessage(inputConfirmPassword);
        }
    }

    private void renderErrorMessage(UIInput errorCausingComponent) {
        FacesMessage errorMessage = new FacesMessage("Password must match confirm password");
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
//        getFacesContext().addMessage(errorCausingComponent.getClientId(), errorMessage);
//        getFacesContext().renderResponse();
    }

    private boolean passwordMatchesWithConfirmPassword(UIInput inputPassword, UIInput inputConfirmPassword) {
        if (isEmptyInput(inputPassword, inputConfirmPassword)) {
            String password = inputPassword.getLocalValue().toString();
            String confirmPassword = inputConfirmPassword.getLocalValue().toString();
            if (confirmPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyInput(UIInput inputPassword, UIInput inputConfirmPassword) {
        return (inputPassword.getLocalValue() != null
                && inputConfirmPassword.getLocalValue() != null);
    }

    private UIInput getInputComponent(UIComponent components, String componentName) {
        return (UIInput) components.findComponent(componentName);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String editProfile() {

        String fileName = file.getFileName();
        int lastIndexOfDot = fileName.lastIndexOf('.');
        String extension = fileName.substring(lastIndexOfDot + 1);

        if (extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg") || extension.equals("gif")) {
            user.setProfileImage(file.getContents());
            userService.updateUser(user);
            return "project";
        }

        addErrorMessage("Only jpeg, jpg, png, gif are allowed", fileUploadComponent);
        return "profile";

    }

    public UIComponent getFileUploadComponent() {
        return fileUploadComponent;
    }

    public void setFileUploadComponent(UIComponent fileUploadComponent) {
        this.fileUploadComponent = fileUploadComponent;
    }

    public StreamedContent getBinaryImage() {
        if (user.getProfileImage() != null) {
            InputStream is = new ByteArrayInputStream(user.getProfileImage());
            StreamedContent image = new DefaultStreamedContent(is, "image/jpeg", "fileName.jpg");
            return image;
        }
        return null;
    }
}
