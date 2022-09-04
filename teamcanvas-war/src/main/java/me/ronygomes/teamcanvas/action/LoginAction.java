package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@Named
@RequestScoped
public class LoginAction {

    private final Logger log = Logger.getLogger(LoginAction.class);

    private String userEmail;
    private String userPassword;
    private boolean rememberMe;
    private UIComponent submitButton;
    private FacesContext facesContext;
    private HttpSession httpSession;

    @EJB
    private UserService userService;

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    @PostConstruct
    public void setUp() {
        facesContext = FacesContext.getCurrentInstance();
        httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
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

    public UIComponent getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(UIComponent submitButton) {
        this.submitButton = submitButton;
    }


    public String login() {
        User user = userService.checkAuthenticity(userEmail, userPassword);
        String outcome = "index";

        if (isUserFound(user)) {
            storeUserInSession(user);
            outcome = "dashboard";
        } else {
            showInvalidUserMessage();
        }

        return outcome;
    }

    public String logout() {
        removeUserLoginInfo();
        return "index.xhtml?faces-redirect=true";
    }

    private void removeUserLoginInfo() {
        httpSession.removeAttribute(LOGIN_USER_SESSION_KEY);
        httpSession.invalidate();
    }

    private void showInvalidUserMessage() {
        FacesMessage invalidUserMessage = new FacesMessage("Invalid username/password");
        facesContext.addMessage(submitButton.getClientId(facesContext), invalidUserMessage);
    }

    private void storeUserInSession(User authenticatedUser) {
        httpSession.setAttribute(LOGIN_USER_SESSION_KEY, authenticatedUser);
        log.info(authenticatedUser.getEmail() + " saved in session");
    }

    private boolean isUserFound(User user) {
        return user != null;
    }
}
