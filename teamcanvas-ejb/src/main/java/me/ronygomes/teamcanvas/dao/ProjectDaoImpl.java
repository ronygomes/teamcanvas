package me.ronygomes.teamcanvas.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.User;
import org.apache.log4j.Logger;

import java.util.List;

@Stateless
public class ProjectDaoImpl implements ProjectDao {

    private final Logger log = Logger.getLogger(ProjectDaoImpl.class);

    private static final String FIND_PROJECT_BY_USER = "SELECT p FROM Project p WHERE p.projectCreator.email = :userEmail";

    private static final String FIND_IN_PROGRESS_PROJECT_BY_USER = "SELECT p FROM Project p" +
            " WHERE p.projectCreator.email = :userEmail AND p.projectStatus = :status";

    @PersistenceContext(unitName = "persistDB")
    private EntityManager em;

    @Override
    public void saveProject(Project projectToSave) {
        projectToSave.setProjectStatus(Project.Status.IN_PROGRESS);
        em.persist(projectToSave);
    }

    @Override
    public Project findProjectById(long projectId) {
        return em.find(Project.class, projectId);
    }

    @Override
    public List<Project> findProjectByUser(User user) {
        TypedQuery<Project> query = em.createQuery(FIND_PROJECT_BY_USER, Project.class);

        query.setParameter("userEmail", user.getEmail());

        return query.getResultList();
    }

    @Override
    public void updateProject(Project project) {
        em.merge(project);
    }

    @Override
    public List<Project> getInProgressProjects(User user) {
        TypedQuery<Project> query = em.createQuery(FIND_IN_PROGRESS_PROJECT_BY_USER, Project.class);
        query.setParameter("userEmail", user.getEmail());
        query.setParameter("status", Project.Status.IN_PROGRESS);

        return query.getResultList();
    }
}
