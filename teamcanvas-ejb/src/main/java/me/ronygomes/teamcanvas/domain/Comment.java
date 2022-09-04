package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project parentProject;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User commentCreatedBy;

    @Column(name = "create_at")
    private Date commentCreationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public User getCommentCreatedBy() {
        return commentCreatedBy;
    }

    public void setCommentCreatedBy(User commentCreatedBy) {
        this.commentCreatedBy = commentCreatedBy;
    }

    public Date getCommentCreationTime() {
        return commentCreationTime;
    }

    public void setCommentCreationTime(Date commentCreationTime) {
        this.commentCreationTime = commentCreationTime;
    }
}
