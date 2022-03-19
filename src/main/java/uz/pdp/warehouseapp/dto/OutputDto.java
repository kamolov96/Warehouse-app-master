package uz.pdp.warehouseapp.dto;

import lombok.*;
import uz.pdp.warehouseapp.entity.Client;
import uz.pdp.warehouseapp.entity.Currency;
import uz.pdp.warehouseapp.entity.Warehouse;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OutputDto {

    private Integer warehouseId;
    private Integer currencyId;
    private Integer clientId;

    private List<OutputProductDto> outputProductDtoList;
}
