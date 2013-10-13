package com.googlecode.teamcanvas.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User implements Serializable{
    @Id
    @Column(name = "user_email")
    private String email;

    @Column(name = "user_first_name")
    private String firstName;

    @Column(name = "user_last_name")
    private String lastName;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @OneToMany(mappedBy = "projectCreator")
    private List<Project> projects = new ArrayList<Project>();

    @OneToMany(mappedBy = "commentCreatedBy")
    private List<Comment> comments = new ArrayList<Comment>();

    @ManyToMany(mappedBy = "teamMembers")
    private List<Team> teams = new ArrayList<Team>();

    @ManyToMany(mappedBy = "assignedToUsers")
    private List<Task> userTasks = new ArrayList<Task>();

    @Lob
    private byte[] profileImage;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean equals(User o){

        if(o != null && o instanceof User){
            return this.email == o.email;
        }
        return false;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
