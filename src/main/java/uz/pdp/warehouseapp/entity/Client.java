package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private boolean isActive;

    public Client(String name, String phoneNumber, boolean isActive) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }
}
