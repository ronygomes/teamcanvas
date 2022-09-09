package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teams")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int TEAM_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTeam")
    @SequenceGenerator(name = "seqTeam", sequenceName = "teams_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(nullable = false, length = TEAM_NAME_LENGTH)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    )
    private List<User> members;

    public Team() {
        this.members = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return getId() == team.getId() && getName().equals(team.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
