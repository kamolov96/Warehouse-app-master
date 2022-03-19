package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.warehouseapp.entity.Currency;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    @Query("select c from Currency  c where c.active=true order by c.name")
    List<Currency>getOnlyActive();
}
