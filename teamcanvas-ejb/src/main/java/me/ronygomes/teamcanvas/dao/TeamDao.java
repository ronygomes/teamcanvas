package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface TeamDao {
    public void saveTeam(Team team);

    public List<Team> findTeamByUser(User creatorOfTeam);

    public Team findTeamById(long teamId);

    public void updateTeam(Team team);

    public void removeTeam(Team team);
}
