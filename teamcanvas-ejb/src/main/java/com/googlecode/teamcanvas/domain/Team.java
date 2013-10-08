package com.googlecode.teamcanvas.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private long id;

    @Column(name = "team_created_at")
    private Date teamCreationTime;

    @Column(name = "team_name")
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User teamCreator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="team_has_user",
                joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "team_id"),
                inverseJoinColumns = @JoinColumn(name = "user_email", referencedColumnName = "user_email")
              )
    private List<User> teamMembers = new ArrayList<User>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTeamCreationTime() {
        return teamCreationTime;
    }

    public void setTeamCreationTime(Date teamCreationTime) {
        this.teamCreationTime = teamCreationTime;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public User getTeamCreator() {
        return teamCreator;
    }

    public void setTeamCreator(User teamCreator) {
        this.teamCreator = teamCreator;
    }

    public List<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<User> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
