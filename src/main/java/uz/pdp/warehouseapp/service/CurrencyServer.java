package uz.pdp.warehouseapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.entity.Currency;
import uz.pdp.warehouseapp.repository.CurrencyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServer {
    final CurrencyRepository currencyRepository;


    public List<Currency> getOnlyActive() {
        return currencyRepository.getOnlyActive();
    }
}
