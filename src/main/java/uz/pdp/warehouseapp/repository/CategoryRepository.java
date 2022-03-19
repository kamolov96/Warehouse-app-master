package uz.pdp.warehouseapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.warehouseapp.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
   @Query(value = "select c from Category c where c.active=true order by c.name")
    List<Category>findCategoryForChoose();
}
