package uz.pdp.warehouseapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
@Entity
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date=Date.valueOf(LocalDate.now());
    @Column(unique = true)
    private String code = String.valueOf(UUID.randomUUID());
    private String factureNumber;
    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private Client client;
    @OneToMany
    private List <OutputProduct> outputProduct ;

}
