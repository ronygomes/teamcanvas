package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class ProjectDetails {

    private final Logger log = LogManager.getLogger(ProjectDetails.class);

    private static final String PROJECT_ID_PARAM_KEY = "project_id";

    private Project project;

    @EJB
    private ProjectService projectService;

    @EJB
    private TaskService taskService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        if (applicationHelper.paramExists(facesContext, PROJECT_ID_PARAM_KEY)) {
            loadProjectFromDatabase();
        }
    }

    private void loadProjectFromDatabase() {
        long projectId = applicationHelper.getLongParamValue(facesContext, PROJECT_ID_PARAM_KEY);
        project = projectService.findProjectById(projectId);
        findAllTaskOfPhases();
    }

    private void findAllTaskOfPhases() {
        List<Phase> phases = project.getPhases();

        for (Phase phase : phases) {
            List<Task> tasks = taskService.findTaskByPhaseId(phase.getId());
            phase.setTasks(tasks);
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
