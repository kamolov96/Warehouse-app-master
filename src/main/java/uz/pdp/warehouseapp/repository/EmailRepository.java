package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouseapp.entity.Email;


@Repository
public interface EmailRepository extends JpaRepository<Email,String> {

}

