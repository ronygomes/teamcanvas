package com.googlecode.teamcanvas.service;

import com.googlecode.teamcanvas.dao.ProjectDao;
import com.googlecode.teamcanvas.dao.UserDao;
import com.googlecode.teamcanvas.domain.Project;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;

@Stateless
public class ProjectServiceImpl implements ProjectService{
    private final Logger log = Logger.getLogger(ProjectServiceImpl.class);

    @EJB
    private ProjectDao projectDao;

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
}
