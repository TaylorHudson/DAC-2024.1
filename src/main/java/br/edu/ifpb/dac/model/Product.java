package br.edu.ifpb.dac.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Table(name = "PRODUCT_TB")
@Entity
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
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
    private Double price;
    @ElementCollection
    @CollectionTable(name = "PRODUCT_IMAGES_TB")
    private Set<String> images;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Category category;

    public Product(String name, String description, Double price, Set<String> images, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.category = category;
    }
}
