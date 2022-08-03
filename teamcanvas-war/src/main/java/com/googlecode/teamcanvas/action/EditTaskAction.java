package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.domain.Task;
import com.googlecode.teamcanvas.service.TaskService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

import java.io.Serializable;

@Named
@RequestScoped
public class EditTaskAction extends AppUtilTemplate implements Serializable {
    private final Logger log = Logger.getLogger(EditTaskAction.class);

    private Task task;
    private final String TEAM_ID_URL_PARAM = "task_id";

    @EJB
    private TaskService taskService;

    @PostConstruct
    public void setUp() {
        initUtilParams();
        initializeTask();
    }


    private void initializeTask() {
        if (paramExists(TEAM_ID_URL_PARAM)) {
            loadFromDatabase();
        } else {
            task = new Task();
        }
    }

    private void loadFromDatabase() {
        task = taskService.findTaskById(getLongParamValue(TEAM_ID_URL_PARAM));
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String editTask() {
        long projectId = getTaskProjectId();
        log.info("Project Id: " + projectId);

        taskService.updateTask(task);
        return "project-details.xhtml?project_id=" + projectId + "&faces-redirect=true";
    }

    private long getTaskProjectId() {
        Project project = taskService.findProjectByTaskId(task.getId());
        return project.getId();
    }
}
