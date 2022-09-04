package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private long id;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "complete_percentage")
    private int completePercentage;

    @Column(name = "task_priority")
    private int taskPriority;

    @Column(name = "task_created_on")
    private Date taskCreationTime;

    @Column(name = "task_modified_on")
    private Date taskLastModifiedTime;

    @Column(name = "task_is_private")
    private boolean isPrivate;

    @Column(name = "task_due_date")
    private Date taskDueDate;

    @Column(name = "task_status")
    private int taskStatus;

    @ManyToOne
    @JoinColumn(name = "phase_id")
    private Phase parentPhase;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User taskCreatedBy;

    @ManyToMany
    @JoinTable(name = "user_has_task",
            joinColumns = @JoinColumn(name = "task_id",
                    referencedColumnName = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_email",
                    referencedColumnName = "user_email")
    )
    private List<User> assignedToUsers;

    public Task() {
        this.assignedToUsers = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getCompletePercentage() {
        return completePercentage;
    }

    public void setCompletePercentage(int completePercentage) {
        this.completePercentage = completePercentage;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Date getTaskCreationTime() {
        return taskCreationTime;
    }

    public void setTaskCreationTime(Date taskCreationTime) {
        this.taskCreationTime = taskCreationTime;
    }

    public Date getTaskLastModifiedTime() {
        return taskLastModifiedTime;
    }

    public void setTaskLastModifiedTime(Date taskLastModifiedTime) {
        this.taskLastModifiedTime = taskLastModifiedTime;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public Phase getParentPhase() {
        return parentPhase;
    }

    public void setParentPhase(Phase parentPhase) {
        this.parentPhase = parentPhase;
    }

    public User getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(User taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public static class Status {
        public static int COMPLETE = 1;
        public static int FAILED = 2;
        public static int IN_PROGRESS = 3;

    }
}
