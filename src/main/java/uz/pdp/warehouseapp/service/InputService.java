package uz.pdp.warehouseapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.InputDto;
import uz.pdp.warehouseapp.dto.InputProductDto;
import uz.pdp.warehouseapp.entity.*;
import uz.pdp.warehouseapp.repository.*;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InputService {
    final ProductRepository productRepository;
    final InputRepository inputRepository;
    final WarehouseRepository warehouseRepository;
    final CurrencyRepository currencyRepository;
    final SupplierRepository supplierRepository;
    final InputProductRepository inputProductRepository;


    public Input saveInput(InputDto inputDto) {
        Warehouse warehouse = warehouseRepository.getById(inputDto.getWarehouse());
        Currency currency = currencyRepository.getById(inputDto.getCurrency());
        Supplier supplier = supplierRepository.getById(inputDto.getSupplier());
        Input save = inputRepository.save(new Input(warehouse, currency, supplier));
        save.setCode("#000" + save.getId());
        save.setFactureNumber("#000" + save.getId() + "W" + save.getWarehouse().getId() + "S" + save.getSupplier().getId() + "C" + save.getCurrency().getId());
        return inputRepository.save(save);
    }

    public Input getInput(Integer id) {
        return inputRepository.getById(id);
    }

    public Input saveProduct(Integer id, InputProductDto inputProductDto) {
        Input input = inputRepository.getById(id);
        Product product = productRepository.getById(inputProductDto.getProduct());

        String[] split = inputProductDto.getExpireDate().split("/");

        LocalDate localDate = null;
        try {

            localDate = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        } catch (DateTimeException e) {
            return new Input();
        }
        Period period=Period.between(LocalDate.now(),localDate);
        int days = period.getDays();
        if(days<=0)
              return new Input();

        InputProduct inputProduct = new InputProduct();
        inputProduct.setInput(input);
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setExpireDate(localDate);
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setProduct(product);
        InputProduct save = inputProductRepository.save(inputProduct);
        List<InputProduct> inputProductList = input.getInputProductList();
        inputProductList.add(save);
        input.setInputProductList(inputProductList);
        return inputRepository.save(input);

    }

    public Page<Input> getAll(PageRequest pageble) {
        return inputRepository.findAll(pageble);
    }

    public Page<InputProduct> getInputProduct(PageRequest pageble, Integer id) {
        return inputProductRepository.getInputId(pageble,id);
    }
}
