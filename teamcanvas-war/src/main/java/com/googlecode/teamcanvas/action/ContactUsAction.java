package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.service.MailSender;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ContactUsAction extends AppUtilTemplate{
    private Logger log = Logger.getLogger(ContactUsAction.class);

    private String message;
    private String infoMessage;

    @Inject
    private MailSender mailSender;

    @PostConstruct
    public void setUp(){
        loadUserFromSession();
    }

    public void sendMail(){
        if(mailSender.sendMail(message)){
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
