package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class InputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate expireDate;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private Integer price;
    @ManyToOne
    private Input input;
    @ManyToOne
    private Product product;

    public InputProduct(LocalDate expireDate, Integer amount, Integer price, Input input, Product product) {
        this.expireDate = expireDate;
        this.amount = amount;
        this.price = price;
        this.input = input;
        this.product = product;
    }
}
