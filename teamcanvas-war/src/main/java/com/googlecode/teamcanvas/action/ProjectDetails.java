package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.Task;
import com.googlecode.teamcanvas.service.ProjectService;
import com.googlecode.teamcanvas.service.TaskService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
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

    private Project project;

    @EJB
    private ProjectService projectService;

    @EJB
    private TaskService taskService;

    private final String PROJECT_ID_PARAM_KEY = "project_id";

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
        List<Phase> phases = project.getProjectPhases();

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
