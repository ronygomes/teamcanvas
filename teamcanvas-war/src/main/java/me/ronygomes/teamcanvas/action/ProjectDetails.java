package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.service.TaskService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.util.List;

@Named
@RequestScoped
public class ProjectDetails extends AppUtilTemplate {

    private final Logger log = Logger.getLogger(ProjectDetails.class);

    private final String PROJECT_ID_PARAM_KEY = "project_id";

    private Project project;

    @EJB
    private ProjectService projectService;

    @EJB
    private TaskService taskService;

    @PostConstruct
    public void setUp() {
        initUtilParams();
        if (paramExists(PROJECT_ID_PARAM_KEY)) {
            loadProjectFromDatabase();
        }
    }

    private void loadProjectFromDatabase() {
        long projectId = getLongParamValue(PROJECT_ID_PARAM_KEY);
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
