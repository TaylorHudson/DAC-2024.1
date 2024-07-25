package br.edu.ifpb.dac.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLE_TB")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role(String name) {
        this.name = name;
    }

}