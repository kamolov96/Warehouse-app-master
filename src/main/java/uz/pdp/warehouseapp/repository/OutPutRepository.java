package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouseapp.entity.Output;

public interface OutPutRepository extends JpaRepository<Output,Integer> {
}
