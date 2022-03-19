package uz.pdp.warehouseapp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private String name;
    private Integer parentCategoryId;
    private boolean active;
}
