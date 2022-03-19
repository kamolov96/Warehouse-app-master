package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    private boolean active=true;

    public Measurement(String name, boolean active) {
        this.name = name;
        this.active = active;
    }
}
