package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private long id;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_creation_time")
    private Date projectCreationTime;

    @Column(name = "project_last_modification_time")
    private Date projectLastModificationTime;

    @Column(name = "project_due_date")
    private Date projectDueDate;

    @Column(name = "project_complete_percentage")
    private int projectCompletePercentage;

    @Column(name = "project_status")
    private int projectStatus;

    @ManyToOne
    @JoinColumn(name = "project_creator_email")
    private User projectCreator;

    @OneToMany(mappedBy = "parentProject", fetch = FetchType.EAGER)
    private List<Phase> projectPhases;

    @OneToMany(mappedBy = "parentProject")
    private List<Comment> projectComments;

    public Project() {
        this.projectPhases = new ArrayList<>();
        this.projectComments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getProjectCreationTime() {
        return projectCreationTime;
    }

    public void setProjectCreationTime(Date projectCreationTime) {
        this.projectCreationTime = projectCreationTime;
    }

    public Date getProjectLastModificationTime() {
        return projectLastModificationTime;
    }

    public void setProjectLastModificationTime(Date projectLastModificationTime) {
        this.projectLastModificationTime = projectLastModificationTime;
    }

    public Date getProjectDueDate() {
        return projectDueDate;
    }

    public void setProjectDueDate(Date projectDueDate) {
        this.projectDueDate = projectDueDate;
    }

    public int getProjectCompletePercentage() {
        return projectCompletePercentage;
    }

    public void setProjectCompletePercentage(int projectCompletePercentage) {
        this.projectCompletePercentage = projectCompletePercentage;
    }

    public User getProjectCreator() {
        return projectCreator;
    }

    public void setProjectCreator(User projectCreator) {
        this.projectCreator = projectCreator;
    }

    public List<Phase> getProjectPhases() {
        return projectPhases;
    }

    public void setProjectPhases(List<Phase> projectPhases) {
        this.projectPhases = projectPhases;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Comment> getProjectComments() {
        return projectComments;
    }

    public void setProjectComments(List<Comment> projectComments) {
        this.projectComments = projectComments;
    }

    public static class Status {
        public static int COMPLETE = 1;
        public static int FAILED = 2;
        public static int IN_PROGRESS = 3;

    }
}
