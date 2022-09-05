package me.ronygomes.teamcanvas.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int EMAIL_LENGTH = 100;
    private static final int FIRST_NAME_LENGTH = 70;
    private static final int LAST_NAME_LENGTH = 50;
    private static final int PASSWORD_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUser")
    @SequenceGenerator(name = "seqUser", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false, updatable = false, unique = true, length = EMAIL_LENGTH)
    private String email;

    @Column(name = "first_name", nullable = false, length = FIRST_NAME_LENGTH)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = LAST_NAME_LENGTH)
    private String lastName;

    @Column(name = "hashed_password", nullable = false, length = PASSWORD_LENGTH)
    private String hashedPassword;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
