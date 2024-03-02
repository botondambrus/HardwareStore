package edu.bbte.idde.abim2109.spring.controller.dto.incoming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class HardwareStoreCreationDTO implements Serializable {

    @NotEmpty
    @Size(max = 256)
    private String name;

    @NotEmpty
    @Size(max = 256)
    private String category;

    @Positive
    private Integer price;

    @Positive
    private Integer quantity;

    @NotEmpty
    @Size(max = 1024)
    private String description;
}
