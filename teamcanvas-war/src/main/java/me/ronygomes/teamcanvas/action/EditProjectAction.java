package me.ronygomes.teamcanvas.action;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import me.ronygomes.teamcanvas.domain.Project;
import me.ronygomes.teamcanvas.helper.ApplicationHelper;
import me.ronygomes.teamcanvas.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Named
@ConversationScoped
public class EditProjectAction implements Serializable {

    private final Logger log = LogManager.getLogger(EditProjectAction.class);

    private Project project;

    @EJB
    private ProjectService projectService;

    @Inject
    private Conversation conversation;

    @Inject
    private ApplicationHelper applicationHelper;

    @PostConstruct
    public void setUp() {
        long id = getProjectId();
        if (isProjectIdParamFound(id)) {
            project = projectService.findProjectById(id);
            log.info("Project Found: " + id + " (" + project + ")");
            startConversation();
        }
    }

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private boolean isProjectIdParamFound(long id) {
        return id != -1;
    }

    private long getProjectId() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        String project_id = parameterMap.get("project_id");
        log.info("URL parameter in edit_project.xhtml (project_id) " + project_id);
        return convertStringToLong(project_id);

    }

    private long convertStringToLong(String project_id) {
        try {
            return Long.parseLong(project_id);
        } catch (NumberFormatException n) {
            return -1L;
        }
    }

    public String editProject() {
        String outcome = "edit_project";
        if (completeEdited()) {
            outcome = "project";
            endConversation();
        }
        return outcome;

    }

    private void endConversation() {
        if (conversation.isTransient()) {
            conversation.end();
        }
    }

    private boolean completeEdited() {
        return projectService.updateProject(project);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getToday() {
        return Calendar.getInstance().getTime();
    }
}
