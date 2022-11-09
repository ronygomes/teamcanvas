package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class EditTaskAction {

    private final Logger log = LogManager.getLogger(EditTaskAction.class);

    private static final String TEAM_ID_URL_PARAM = "task_id";

    private Task task;

    @EJB
    private TaskService taskService;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        initializeTask();
    }

    private void initializeTask() {
        if (applicationHelper.paramExists(facesContext, TEAM_ID_URL_PARAM)) {
            loadFromDatabase();
        } else {
            task = new Task();
        }
    }

    private void loadFromDatabase() {
        task = taskService.findTaskById(applicationHelper.getLongParamValue(facesContext, TEAM_ID_URL_PARAM));
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
