package com.googlecode.teamcanvas.action;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class RegisterBean implements Serializable{
    private String userEmail;
    private String firstName;
    private String lastName;
    private String userPassword;
    private String confirmPassword;


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
