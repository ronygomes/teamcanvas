package com.googlecode.teamcanvas.action;


import com.googlecode.teamcanvas.domain.Project;
import com.googlecode.teamcanvas.service.ProjectService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@ConversationScoped
public class EditProjectAction extends UserLoginTemplate implements Serializable{
    private final Logger log = Logger.getLogger(EditProjectAction.class);

    private Project project;

    @EJB
    private ProjectService projectService;
    @Inject
    private Conversation conversation;

    @PostConstruct
    public void setUp(){
        long id = getProjectId();
        if(isProjectIdParamFound(id)){
            project = projectService.findProjectById(id);
            log.info("Project Found: " + id + " (" + project + ")");
            startConversation();
        }
    }

    private void startConversation() {
        if(conversation.isTransient()){
            conversation.begin();
        }
    }

    private boolean isProjectIdParamFound(long id) {
        return id != -1;
    }

    private long getProjectId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        String project_id = parameterMap.get("project_id");
        log.info("URL parameter in edit_project.xhtml (project_id) " + project_id);
        return convertStringToLong(project_id);

    }

    private long convertStringToLong(String project_id) {
        try {
            return Long.parseLong(project_id);
        }catch (NumberFormatException n){
            return -1L;
        }
    }

    public String editProject(){
        String outcome = "edit_project";
        if(completeEdited()){
            outcome = "project";
            endConversation();
        }
        return outcome;

    }

    private void endConversation() {
        if(conversation.isTransient()){
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
}
