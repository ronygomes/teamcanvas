package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phases")
public class Phase implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int PHASE_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPhase")
    @SequenceGenerator(name = "seqPhase", sequenceName = "phases_id_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false, length = PHASE_NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    private int ordinal;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "phase")
    private List<Task> tasks;

    public Phase() {
        this.tasks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
