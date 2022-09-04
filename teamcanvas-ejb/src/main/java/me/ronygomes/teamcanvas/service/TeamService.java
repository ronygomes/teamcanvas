package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;

import java.util.List;

public interface TeamService {
    public List<Team> findTeamByOwner(User creatorOfTeam);

    public boolean saveTeam(Team team);

    public Team findTeamById(long teamId);

    public void updateTeam(Team team);

    public void removeTeam(long teamId);

    public void removeMemberFromTeam(long teamId, String memberId);
}
