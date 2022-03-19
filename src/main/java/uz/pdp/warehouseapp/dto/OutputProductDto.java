package uz.pdp.warehouseapp.dto;

import lombok.*;
import uz.pdp.warehouseapp.entity.Output;
import uz.pdp.warehouseapp.entity.Product;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OutputProductDto {
    private Integer amount;
    private double price;
    private Integer productId;

}
