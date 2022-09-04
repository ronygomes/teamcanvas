package me.ronygomes.teamcanvas.action;


import me.ronygomes.teamcanvas.domain.Phase;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.service.ProjectService;
import me.ronygomes.teamcanvas.template.AppUtilTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.apache.log4j.Logger;

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
    public void setUp() {
        initUtilParams();
        initializePhase();

        if (paramExists(PROJECT_ID_PARAM_KEY)) {
            loadProjectFromDatabase();
        } else {
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

    public String addPhaseToProject() {
        log.info("Phase : " + phase.getPhaseName());
        log.info("Project:" + project.getId());
        project = projectService.findProjectById(project.getId());

        project.getProjectPhases().add(phase);
        projectService.addPhase(project, phase);

        return "project-details.xhtml?project_id=" + project.getId() + "&faces-redirect=true";
    }
}
