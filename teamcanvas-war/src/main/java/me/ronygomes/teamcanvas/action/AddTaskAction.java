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
import org.apache.log4j.Logger;

@Named
@RequestScoped
public class AddTaskAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(AddTaskAction.class);

    private Phase phase;
    private Task task;

    @EJB
    private PhaseService phaseService;

    @EJB
    private TaskService taskService;

    private final String PHASE_ID_PARAM_KEY = "phase_id";

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
        log.info("Phase : " + phase.getPhaseName());
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
        log.info("Task : " + task.getTaskTitle());

        phase = phaseService.findPhaseById(phase.getId());
        task.setParentPhase(phase);

        taskService.saveTask(task);
        return "project-details.xhtml?project_id=" + phase.getParentProject().getId() + "&faces-redirect=true";

    }
}
