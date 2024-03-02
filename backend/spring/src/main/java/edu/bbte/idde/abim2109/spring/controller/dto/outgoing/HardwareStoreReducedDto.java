package edu.bbte.idde.abim2109.spring.controller.dto.outgoing;

import lombok.Data;

import java.io.Serializable;

@Data
public class HardwareStoreReducedDto implements Serializable {
    private Long id;
    private String name;
    private String category;
    private Integer price;
    private Integer quantity;
}
