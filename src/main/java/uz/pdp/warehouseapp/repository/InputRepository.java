package uz.pdp.warehouseapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouseapp.entity.Input;
import uz.pdp.warehouseapp.entity.InputProduct;

@Repository
public interface InputRepository extends JpaRepository<Input,Integer> {
    @Override
    Page<Input> findAll(Pageable pageable);

}
