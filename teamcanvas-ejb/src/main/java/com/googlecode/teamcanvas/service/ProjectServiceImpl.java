package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.PhaseDao;
import com.googlecode.teamcanvas.dao.ProjectDao;
import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;

@Stateless
public class ProjectServiceImpl implements ProjectService{
    private final Logger log = Logger.getLogger(ProjectServiceImpl.class);

    @EJB
    private ProjectDao projectDao;
    @EJB
    private PhaseDao phaseDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean saveProject(Project projectToSave){
        try{
            projectDao.saveProject(projectToSave);
            return true;
        }catch (PersistenceException e) {
            log.info("Unable to save project:" + projectToSave.getProjectTitle(), e);
        }
        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Project> findProjectByUser(User user) {
        List<Project> projects;
        try{
            if(user != null){
                projects = projectDao.findProjectByUser(user);
                return projects;
            }
            log.info("Find project by user : User is null");
            return Collections.emptyList();

        }catch (PersistenceException e){
            log.info("Find project by user exception: " + user.getEmail(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Project findProjectById(long id) {
        Project project = null;
        try{
            project = projectDao.findProjectById(id);
            log.info("Project found id: " + id);
        }catch (PersistenceException e){
            log.info("Project not found by id " + id, e);
        }
        return project;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean updateProject(Project project) {
        try{
            projectDao.updateProject(project);
            return true;
        }catch (PersistenceException e) {
            log.info("Unable to update project:" + project.getProjectTitle(), e);
        }
        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addPhase(Project project, Phase phase) {

        phase.setParentProject(project);
        phaseDao.savePhase(phase);

        projectDao.updateProject(project);
    }


}
