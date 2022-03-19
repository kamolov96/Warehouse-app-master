package uz.pdp.warehouseapp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String name;
    private Integer category;
    private Integer measurement;
    private boolean active;

}
