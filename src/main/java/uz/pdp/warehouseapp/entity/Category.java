package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToOne()
    private Category parentCategoryId;
    private boolean active=true;

    public Category(String name, Category parentCategoryId, boolean active) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
        this.active = active;
    }

    public Category(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                ", active=" + active +
                '}';
    }
}
