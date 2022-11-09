package me.ronygomes.teamcanvas.helper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import me.ronygomes.teamcanvas.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

@Named
@ApplicationScoped
public class ApplicationHelper {

    private final Logger log = LogManager.getLogger(ApplicationHelper.class);

    private static final String LOGIN_USER_SESSION_KEY = "loggedInUser";

    public User getLoggedInUser(FacesContext context) {
        Objects.requireNonNull(context, "FacesContext can not be null");

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        return (User) httpSession.getAttribute(LOGIN_USER_SESSION_KEY);
    }

    public boolean isLoggedInUserFound(FacesContext context) {
        return getLoggedInUser(context) != null;
    }

    public Map<String, String> getParameterMap(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap();
    }

    public boolean paramExists(FacesContext context, String paramName) {
        return getParameterMap(context).get(paramName) != null;
    }

    public long getLongParamValue(FacesContext context, String paramName) {
        String paramValue = getParameterMap(context).get(paramName);
        log.info("Parameter:(" + paramName + " ) " + paramValue);
        return convertStringToLong(paramValue);
    }

    private long convertStringToLong(String stringValue) {
        try {
            return Long.parseLong(stringValue);
        } catch (NumberFormatException n) {
            log.info("Unable to convert " + stringValue);
            return -1L;
        }
    }

    public void addErrorMessage(FacesContext context, String message, UIComponent component) {
        FacesMessage invalidUserMessage = new FacesMessage(message);
        context.addMessage(component.getClientId(context), invalidUserMessage);
    }

    public String getStringParamValue(FacesContext context, String paramName) {
        String paramValue = getParameterMap(context).get(paramName);
        log.info("Parameter:(" + paramName + ") " + paramValue);
        return paramValue;
    }
}
