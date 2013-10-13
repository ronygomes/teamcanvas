package com.googlecode.teamcanvas.dao;

import com.googlecode.teamcanvas.domain.Team;
import com.googlecode.teamcanvas.domain.User;

import java.util.List;

public interface TeamDao {
    public void saveTeam(Team team);
    public List<Team> findTeamByUser(User creatorOfTeam);
    public Team findTeamById(long teamId);
    public void updateTeam(Team team);
    public void removeTeam(Team team);
}
