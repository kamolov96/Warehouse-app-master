package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouseapp.entity.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
   @Query("select m from Measurement m where m.active=true order by m.name")
    List<Measurement> getMeasurementForChoose();
}
