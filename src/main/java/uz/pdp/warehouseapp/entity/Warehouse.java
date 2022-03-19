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
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    private boolean active=true;
    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<User>users;


}
