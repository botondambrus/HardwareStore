package edu.bbte.idde.abim2109.spring.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PurchaseFilter {
    private String name;
    private Integer minQuantity;
    private Integer maxQuantity;
}
