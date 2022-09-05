package me.ronygomes.teamcanvas.service;

import me.ronygomes.teamcanvas.dao.PhaseDao;
import me.ronygomes.teamcanvas.dao.ProjectDao;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.PersistenceException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

@Stateless
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = Logger.getLogger(ProjectServiceImpl.class);

    @EJB
    private ProjectDao projectDao;

    @EJB
    private PhaseDao phaseDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveProject(Project projectToSave) {

        try {
            projectDao.saveProject(projectToSave);
            return true;
        } catch (PersistenceException e) {
            log.info("Unable to save project:" + projectToSave.getTitle(), e);
        }

        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Project> findProjectByUser(User user) {

        List<Project> projects;

        try {
            if (user != null) {
                projects = projectDao.findProjectByUser(user);
                return projects;
            }

            log.info("Find project by user : User is null");
            return Collections.emptyList();

        } catch (PersistenceException e) {
            log.info("Find project by user exception: " + user.getEmail(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Project findProjectById(long id) {
        Project project = null;

        try {
            project = projectDao.findProjectById(id);
            log.info("Project found id: " + id);
        } catch (PersistenceException e) {
            log.info("Project not found by id " + id, e);
        }

        return project;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean updateProject(Project project) {
        try {
            projectDao.updateProject(project);
            return true;
        } catch (PersistenceException e) {
            log.info("Unable to update project:" + project.getTitle(), e);
        }

        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addPhase(Project project, Phase phase) {

        phase.setProject(project);
        phaseDao.savePhase(phase);

        projectDao.updateProject(project);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Project> getInProgressProject(User user) {
        List<Project> projects = projectDao.getInProgressProjects(user);
        loadPhases(projects);

        return projects;
    }

    private void loadPhases(List<Project> projects) {
        for (Project project : projects) {
            project.getPhases();
        }
    }
}
