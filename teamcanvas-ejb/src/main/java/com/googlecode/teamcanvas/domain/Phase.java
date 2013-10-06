package com.googlecode.teamcanvas.domain;

public class Phase {
    private long id;
    private String phaseName;
    private int phaseOrdinal;
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
