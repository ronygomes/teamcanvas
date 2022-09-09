package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int PROJECT_NAME_LENGTH = 100;
    private static final int PROJECT_DESCRIPTION_LENGTH = 2000;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProject")
    @SequenceGenerator(name = "seqProject", sequenceName = "projects_id_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false, length = PROJECT_NAME_LENGTH)
    private String title;

    @Column(length = PROJECT_DESCRIPTION_LENGTH)
    private String description;

    @Column(name = "creation_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "last_modification_date", insertable = false)
    private Date lastModificationDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "complete_percentage")
    private int completePercentage;

    private int status;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Phase> phases;

    public Project() {
        this.phases = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getCompletePercentage() {
        return completePercentage;
    }

    public void setCompletePercentage(int completePercentage) {
        this.completePercentage = completePercentage;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getId() == project.getId() && getTitle().equals(project.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    public static class Status {
        public static int COMPLETE = 1;
        public static int FAILED = 2;
        public static int IN_PROGRESS = 3;

    }
}
