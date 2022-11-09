package me.ronygomes.teamcanvas.action;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.service.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class ContactUsAction {

    private final Logger log = LogManager.getLogger(ContactUsAction.class);

    private String message;
    private String infoMessage;

    @Inject
    private MailSender mailSender;

    public void sendMail() {
        if (mailSender.sendMail(message)) {
            infoMessage = "Message has been send!";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }
}
