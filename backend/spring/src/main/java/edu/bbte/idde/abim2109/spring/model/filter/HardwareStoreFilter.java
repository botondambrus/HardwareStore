package edu.bbte.idde.abim2109.spring.model.filter;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class HardwareStoreFilter {
    private String category;
    private String name;
    private Integer minPrice;
    private Integer maxPrice;
}