package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.Task;
import com.googlecode.teamcanvas.service.PhaseService;
import com.googlecode.teamcanvas.service.ProjectService;
import com.googlecode.teamcanvas.service.TaskService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
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
    public void setUp(){
        initUtilParams();
        if(paramExists(PROJECT_ID_PARAM_KEY)){
            loadProjectFromDatabase();
        }
    }

    private void loadProjectFromDatabase() {
        long projectId = getParamValue(PROJECT_ID_PARAM_KEY);
        project = projectService.findProjectById(projectId);
        findAllTaskOfPhases();
    }

    private void findAllTaskOfPhases() {
        List<Phase> phases = project.getProjectPhases();

        for(Phase phase : phases){
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
