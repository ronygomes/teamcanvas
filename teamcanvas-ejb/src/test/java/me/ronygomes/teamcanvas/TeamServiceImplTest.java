package me.ronygomes.teamcanvas;

import jakarta.persistence.PersistenceException;
import me.ronygomes.teamcanvas.dao.TeamDao;
import me.ronygomes.teamcanvas.dao.UserDao;
import me.ronygomes.teamcanvas.domain.Team;
import me.ronygomes.teamcanvas.domain.User;
import me.ronygomes.teamcanvas.service.TeamService;
import me.ronygomes.teamcanvas.service.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamServiceImplTest {

    private static final String TEST_USER_EMAIL = "john@exmaple.com";

    private UserDao userDao;
    private TeamDao teamDao;
    private TeamService teamService;

    @BeforeEach
    void setup() {
        this.userDao = Mockito.mock(UserDao.class);
        this.teamDao = Mockito.mock(TeamDao.class);
        this.teamService = new TeamServiceImpl(userDao, teamDao);
    }

    @Test
    void testFindTeamByOwner() {
        List<Team> expected = Arrays.asList(new Team());
        User owner = new User();

        Mockito.when(teamDao.findTeamByUser(owner)).thenReturn(expected);
        Assertions.assertSame(expected, teamService.findTeamByOwner(owner));
    }

    @Test
    void testSaveTeam() {
        Team team = new Team();
        Assertions.assertTrue(teamService.saveTeam(team));
        Mockito.verify(teamDao, Mockito.times(1)).saveTeam(team);
    }

    @Test
    void testSaveTeamException() {
        Team team = new Team();
        Mockito.doThrow(PersistenceException.class).when(teamDao).saveTeam(team);

        Assertions.assertFalse(teamService.saveTeam(team));
        Mockito.verify(teamDao, Mockito.times(1)).saveTeam(team);
    }

    @Test
    void testFindTeamById() {
        Team team = new Team();
        Mockito.when(teamDao.findTeamById(1L)).thenReturn(team);

        Assertions.assertSame(team, teamService.findTeamById(1L));
    }

    @Test
    void testFindTeamByIdException() {
        Mockito.when(teamDao.findTeamById(1L)).thenThrow(PersistenceException.class);
        Assertions.assertNull(teamService.findTeamById(1L));
    }

    @Test
    void testUpdateTeam() {
        Team team = new Team();
        teamService.updateTeam(team);

        Mockito.verify(teamDao, Mockito.times(1)).updateTeam(team);
    }

    @Test
    void testRemoveTeam() {
        Team team = new Team();
        Mockito.when(teamDao.findTeamById(1L)).thenReturn(team);
        teamService.removeTeam(1L);
        Mockito.verify(teamDao, Mockito.times(1)).removeTeam(team);
    }

    @Test
    void testRemoveMemberFromTeam() {
        Team team = Mockito.mock(Team.class);
        User m1 = new User();
        User m2 = new User();

        List<User> members = new ArrayList<>();
        members.add(m1);
        members.add(m2);

        Mockito.when(team.getMembers()).thenReturn(members);
        Mockito.when(teamDao.findTeamById(1L)).thenReturn(team);
        Mockito.when(userDao.findUserByEmail(TEST_USER_EMAIL)).thenReturn(m1);

        teamService.removeMemberFromTeam(1L, TEST_USER_EMAIL);
        ArgumentCaptor<Team> ac = ArgumentCaptor.forClass(Team.class);
        Mockito.doNothing().when(teamDao).updateTeam(ac.capture());

        Assertions.assertEquals(1, team.getMembers().size());
        Assertions.assertSame(m2, team.getMembers().get(0));
    }
}
