package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

@Named
@RequestScoped
public class CreateNewProjectAction {

    private final Logger log = LogManager.getLogger(CreateNewProjectAction.class);

    private Project project;

    @EJB
    private ProjectService projectService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    private void setUp() {
        initializeProject();
    }

    private void initializeProject() {
        if (project == null) {
            project = new Project();
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String createProject() {
        String outcome = "create-new-project";
        if (applicationHelper.isLoggedInUserFound(facesContext)) {
            configureProject();
            if (saveProject()) {
                outcome = "project.xhtml?faces-redirect=true";
            }
        }
        return outcome;
    }

    private boolean saveProject() {
        return projectService.saveProject(project);
    }

    private void configureProject() {
        setLoggedInUserAsProjectCreator();
        setCurrentTimeAsProjectCreationTime();
    }

    private void setCurrentTimeAsProjectCreationTime() {
        Date currentTime = Calendar.getInstance().getTime();
        project.setCreationDate(currentTime);
    }

    private void setLoggedInUserAsProjectCreator() {
        project.setCreator(applicationHelper.getLoggedInUser(facesContext));
    }
}
