package edu.bbte.idde.abim2109.spring.controller.dto.outgoing;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PurchaseDTO implements Serializable {
    private Long id;
    private Integer quantity;
    private String name;
    private String address;
    private String email;
    private Date date;
    private Long hardwareStoreId;
}
