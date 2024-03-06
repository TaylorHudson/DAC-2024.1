package br.edu.ifpb.dac.model;

import br.edu.ifpb.dac.model.enumeration.UserType;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "USER_TB")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String document;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    public User(Long id, String firstName, String lastName, String email, String password, String document, UserType type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.document = document;
        this.type = type;
    }

    public User(String firstName, String lastName, String email, String password, String document, UserType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.document = document;
        this.type = type;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getDocument(), user.getDocument()) && getType() == user.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", document='" + document + '\'' +
                ", type=" + type +
                '}';
    }
}