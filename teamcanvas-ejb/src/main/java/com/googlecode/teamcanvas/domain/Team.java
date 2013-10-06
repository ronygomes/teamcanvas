package com.googlecode.teamcanvas.domain;

import java.util.Date;

public class Team {
    private long id;
    private Date teamCreationTime;
    private String teamName;
    private User teamCreator;

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
}
