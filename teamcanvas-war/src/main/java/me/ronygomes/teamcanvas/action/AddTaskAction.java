package me.ronygomes.teamcanvas.action;

import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Task;
import me.ronygomes.teamcanvas.service.PhaseService;
import me.ronygomes.teamcanvas.service.TaskService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

@Named
@RequestScoped
public class AddTaskAction extends AppUtilTemplate {

    private final Logger log = LogManager.getLogger(AddTaskAction.class);

    private static final String PHASE_ID_PARAM_KEY = "phase_id";

    private Phase phase;
    private Task task;

    @EJB
    private PhaseService phaseService;

    @EJB
    private TaskService taskService;

    @PostConstruct
    public void setUp() {
        log.info("Initializing AddTaskAction");
        initUtilParams();
        initializePhase();
        initializeNewTask();
    }

    private void initializePhase() {
        if (paramExists(PHASE_ID_PARAM_KEY)) {
            loadPhaseFromDatabase();
        } else {
            initializeNewPhase();
        }
    }

    private void initializeNewTask() {
        task = new Task();
    }

    private void initializeNewPhase() {
        phase = new Phase();
    }

    private void loadPhaseFromDatabase() {
        long phaseId = getLongParamValue(PHASE_ID_PARAM_KEY);
        phase = phaseService.findPhaseById(phaseId);
        log.info("Phase : " + phase.getName());
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String addTaskToPhase() {
        log.info("Phase id: " + phase.getId());
        log.info("Task : " + task.getTitle());

        phase = phaseService.findPhaseById(phase.getId());
        task.setPhase(phase);
        task.setCreator(getLoggedInUser());

        taskService.saveTask(task, new Date());
        return "project-details.xhtml?project_id=" + phase.getProject().getId() + "&faces-redirect=true";

    }
}
