package uz.pdp.warehouseapp.dto;

import lombok.Data;

@Data
public class InputProductDto {
    private String expireDate;
    private Integer amount;
    private Integer price;
    private Integer input;
    private Integer product;
}
