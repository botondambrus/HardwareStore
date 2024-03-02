package edu.bbte.idde.abim2109.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "db_hardwarestore")
public class HardwareStore extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(length = 1024)
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer quantity;


    @OneToMany(mappedBy = "hardwareStore", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    public HardwareStore(String name, String category, String description, Integer price, Integer quantity) {
        super();
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}