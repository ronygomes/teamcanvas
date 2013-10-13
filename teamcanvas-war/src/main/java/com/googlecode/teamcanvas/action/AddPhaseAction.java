package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Phase;
import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.service.ProjectService;
import com.googlecode.teamcanvas.template.AppUtilTemplate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AddPhaseAction extends AppUtilTemplate {
    private final Logger log = Logger.getLogger(AddPhaseAction.class);

    private Project project;
    private Phase phase;

    @EJB
    private ProjectService projectService;

    private final String PROJECT_ID_PARAM_KEY = "project_id";

    @PostConstruct
    public void setUp(){
        initUtilParams();
        initializePhase();

        if(paramExists(PROJECT_ID_PARAM_KEY)){
            loadProjectFromDatabase();
        }else{
            project = new Project();
        }
    }

    private void initializePhase() {
        phase = new Phase();
    }

    private void loadProjectFromDatabase() {
        long projectId = getLongParamValue(PROJECT_ID_PARAM_KEY);
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

    public String addPhaseToProject(){
        log.info("Phase : " + phase.getPhaseName());
        log.info("Project:" + project.getId());
        project = projectService.findProjectById(project.getId());

        project.getProjectPhases().add(phase);
        projectService.addPhase(project, phase);

        return "project-details.xhtml?project_id="+project.getId()+"&faces-redirect=true";
    }
}
