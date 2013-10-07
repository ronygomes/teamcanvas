package com.googlecode.teamcanvas.domain;

import javax.persistence.*;

@Entity
@Table(name = "phase")
public class Phase {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "phase_id")
    private long id;

    @Column(name = "phase_name")
    private String phaseName;

    @Column(name = "phase_ordinal")
    private int phaseOrdinal;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project parentProject;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getPhaseOrdinal() {
        return phaseOrdinal;
    }

    public void setPhaseOrdinal(int phaseOrdinal) {
        this.phaseOrdinal = phaseOrdinal;
    }

    public Project getParentProject() {
        return parentProject;
    }

    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }
}
