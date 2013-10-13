package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Task;
import com.googlecode.teamcanvas.service.PhaseService;
import com.googlecode.teamcanvas.service.TaskService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

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
    public void setUp(){
        log.info("Initializing AddTaskAction");
        initUtilParams();
        initializePhase();
        initializeNewTask();
    }

    private void initializePhase() {
        if(paramExists(PHASE_ID_PARAM_KEY)){
            loadPhaseFromDatabase();
        }else{
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

    public String addTaskToPhase(){
        log.info("Phase id: " + phase.getId());
        log.info("Task : " + task.getTaskTitle());

        phase = phaseService.findPhaseById(phase.getId());
        task.setParentPhase(phase);

        taskService.saveTask(task);
        return "project-details.xhtml?project_id=" + phase.getParentProject().getId() + "&faces-redirect=true";

    }
}
