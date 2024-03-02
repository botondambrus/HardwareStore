package edu.bbte.idde.abim2109.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Purchase extends BaseEntity {
    private Integer hardwareId;
    private Integer quantity;
    private String name;
    private String address;
    private String email;
}
