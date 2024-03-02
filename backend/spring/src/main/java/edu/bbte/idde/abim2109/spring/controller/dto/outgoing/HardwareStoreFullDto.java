package edu.bbte.idde.abim2109.spring.controller.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HardwareStoreFullDto extends HardwareStoreReducedDto {
    String description;
}
