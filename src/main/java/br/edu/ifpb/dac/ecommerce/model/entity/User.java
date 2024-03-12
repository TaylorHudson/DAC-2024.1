package br.edu.ifpb.dac.ecommerce.model.entity;

import br.edu.ifpb.dac.ecommerce.model.entity.enumeration.UserType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "USER_TB")
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    public User(String firstName, String lastName, String email, String password, String document, UserType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.document = document;
        this.type = type;
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
