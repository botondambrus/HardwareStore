package edu.bbte.idde.abim2109.spring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "db_purchase")
public class Purchase extends BaseEntity {
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date date;

    @JoinColumn(name = "hardware_store_id", nullable = false)
    @ManyToOne(optional = false)
    private HardwareStore hardwareStore;
}