package com.googlecode.teamcanvas.domain;


import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "project")
public class Project {
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

    @ManyToOne
    @JoinColumn(name = "project_creator_email")
    private User projectCreator;

    @OneToMany(mappedBy = "parentProject", fetch = FetchType.EAGER)
    private List<Phase> projectPhases = new ArrayList<Phase>();

    @OneToMany(mappedBy = "parentProject")
    private List<Comment> projectComments = new ArrayList<Comment>();

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
}
