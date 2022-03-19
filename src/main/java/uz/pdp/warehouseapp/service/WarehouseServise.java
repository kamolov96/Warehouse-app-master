package uz.pdp.warehouseapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.entity.Warehouse;
import uz.pdp.warehouseapp.repository.WarehouseRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WarehouseServise {
    final WarehouseRepository warehouseRepository;
    public List<Warehouse> getAllActive() {
        return warehouseRepository.getOnlyActive();
    }
}
