package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouseapp.entity.OutputProduct;

public interface OutPutProductRepository extends JpaRepository<OutputProduct,Integer> {
}
