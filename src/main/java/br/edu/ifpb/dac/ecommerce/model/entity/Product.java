package br.edu.ifpb.dac.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Table(name = "PRODUCT_TB")
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PRODUCT_IMAGES_TB")
    private Set<String> images;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Category category;

    public Product(String name, String description, Double price, Set<String> images, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                ", category=" + category.getName() +
                '}';
    }
}
