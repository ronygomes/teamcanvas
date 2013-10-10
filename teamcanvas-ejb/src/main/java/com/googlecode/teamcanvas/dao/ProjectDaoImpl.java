package com.googlecode.teamcanvas.dao;


import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProjectDaoImpl implements ProjectDao{
    private final Logger log = Logger.getLogger(ProjectDaoImpl.class);
    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    private String FIND_PROJECT_BY_USER = "SELECT p FROM Project p WHERE p.projectCreator.id = :userEmail";
    private static final String FIND_PROJECT_BY_USER_EAGER_FETCH = "SELECT p FROM Project p "  +
            "LEFT JOIN FETCH p.projectPhases h " +
            "LEFT JOIN FETCH h.tasks t " +
            "WHERE p.projectCreator.email = :userEmail";

    @Override
    public void saveProject(Project projectToSave) {
        em.persist(projectToSave);
    }

    @Override
    public Project findProjectById(long projectId) {
        return em.find(Project.class, projectId);
    }

    @Override
    public List<Project> findProjectByUser(User user) {
        TypedQuery<Project> query = em.createQuery(FIND_PROJECT_BY_USER, Project.class);
        //TypedQuery<Project> query2 = em.createQuery(FIND_PROJECT_BY_USER_EAGER_FETCH, Project.class);

        query.setParameter("userEmail", user.getEmail());
        List<Project> projects = query.getResultList();

        return projects;
    }

    @Override
    public void updateProject(Project project) {
        em.merge(project);
    }


}
