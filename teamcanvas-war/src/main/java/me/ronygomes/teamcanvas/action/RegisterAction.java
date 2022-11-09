package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class RegisterAction {

    private final Logger log = LogManager.getLogger(RegisterAction.class);

    private static final String INPUT_PASSWORD_VIEW_ID = "password";
    private static final String INPUT_CONFIRM_PASSWORD_VIEW_ID = "confirmPassword";

    private User user;
    private String confirmPassword;
    private FacesContext facesContext;
    private UIComponent registerButton;

    @EJB
    private UserService userService;

    @PostConstruct
    public void setUp() {
        if (user == null) {
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

    public String register() {
        if (!isSuccessfullyRegistered()) {
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
        try {
            isSuccessful = userService.saveUser(user);
        } catch (EJBException e) {
            log.info(user.getEmail() + " already exists");
        }
        return isSuccessful;

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
        facesContext.addMessage(errorCausingComponent.getClientId(), errorMessage);
        facesContext.renderResponse();
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
}
