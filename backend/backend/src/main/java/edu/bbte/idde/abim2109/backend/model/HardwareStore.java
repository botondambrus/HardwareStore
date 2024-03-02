package edu.bbte.idde.abim2109.backend.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class HardwareStore extends BaseEntity {
    private String name;
    private String category;
    private String description;
    private Integer price;
    private Integer quantity;
}