package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OutputProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private double price;
//    @ManyToOne
//    private Output output;
    @ManyToOne
    private Product product;


}
