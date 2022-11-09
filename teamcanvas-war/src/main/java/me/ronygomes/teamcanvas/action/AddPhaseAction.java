package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class AddPhaseAction {

    private final Logger log = LogManager.getLogger(AddPhaseAction.class);

    private static final String PROJECT_ID_PARAM_KEY = "project_id";

    private Project project;
    private Phase phase;

    @EJB
    private ProjectService projectService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        initializePhase();

        if (applicationHelper.paramExists(facesContext, PROJECT_ID_PARAM_KEY)) {
            loadProjectFromDatabase();
        } else {
            project = new Project();
        }
    }

    private void initializePhase() {
        phase = new Phase();
    }

    private void loadProjectFromDatabase() {
        long projectId = applicationHelper.getLongParamValue(facesContext, PROJECT_ID_PARAM_KEY);
        project = projectService.findProjectById(projectId);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String addPhaseToProject() {
        log.info("Phase : " + phase.getName());
        log.info("Project:" + project.getId());
        project = projectService.findProjectById(project.getId());

        project.getPhases().add(phase);
        projectService.addPhase(project, phase);

        return "project-details.xhtml?project_id=" + project.getId() + "&faces-redirect=true";
    }
}
