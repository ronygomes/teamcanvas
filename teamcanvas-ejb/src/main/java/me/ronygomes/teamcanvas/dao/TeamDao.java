package me.ronygomes.teamcanvas.dao;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface TeamDao {

    void saveTeam(Team team);

    List<Team> findTeamByUser(User creatorOfTeam);

    Team findTeamById(long teamId);

    void updateTeam(Team team);

    void removeTeam(Team team);
}
