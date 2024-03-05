package br.edu.ifpb.dac.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Table(name = "PRODUCT_TB")
@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String price;
    @ElementCollection
    @CollectionTable(name = "PRODUCT_IMAGES_TB")
    private Set<String> images;
}
