package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date=Date.valueOf(LocalDate.now());
    private String code= String.valueOf(UUID.randomUUID());
    private String factureNumber;
    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "input", cascade = CascadeType.ALL)
    private List<InputProduct> inputProductList=new ArrayList<>();

    public Input(Warehouse warehouse, Currency currency, Supplier supplier) {
        this.warehouse = warehouse;
        this.currency = currency;
        this.supplier = supplier;
    }
}
