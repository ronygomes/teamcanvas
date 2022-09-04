package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface TeamService {

    List<Team> findTeamByOwner(User creatorOfTeam);

    boolean saveTeam(Team team);

    Team findTeamById(long teamId);

    void updateTeam(Team team);

    void removeTeam(long teamId);

    void removeMemberFromTeam(long teamId, String memberId);
}
