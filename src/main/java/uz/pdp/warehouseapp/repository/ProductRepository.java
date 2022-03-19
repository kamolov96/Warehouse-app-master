package uz.pdp.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouseapp.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Override
   Page<Product> findAll(Pageable pageable);
}
