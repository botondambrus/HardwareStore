package edu.bbte.idde.abim2109.spring.controller.dto.incoming;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PurchaseCreationDTO {
    @Positive
    private Integer quantity;

    @NotEmpty
    @Size(max = 256)
    private String name;

    @NotEmpty
    @Size(max = 1024)
    private String address;

    @NotEmpty
    @Size(max = 256)
    private String email;
}
