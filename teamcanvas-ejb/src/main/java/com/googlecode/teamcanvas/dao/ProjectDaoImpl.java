package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.Project;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProjectDaoImpl implements ProjectDao{
    private final Logger log = Logger.getLogger(ProjectDaoImpl.class);

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @Override
    public void saveProject(Project projectToSave) {
        em.persist(projectToSave);
    }

    @Override
    public Project findProjectById(Long projectId) {
        return em.find(Project.class, projectId);
    }
}
