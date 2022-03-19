package uz.pdp.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.OutputDto;
import uz.pdp.warehouseapp.dto.OutputProductDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.*;
import uz.pdp.warehouseapp.repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutPutService {
    @Autowired
    OutPutProductRepository outPutProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutPutRepository outPutRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    public List<Output> getAll() {
        return outPutRepository.findAll();
    }
     public List<Warehouse> getAllWarehouse(){
         List<Warehouse> all = warehouseRepository.findAll();
         return all;
     }
    List<Integer>productList=new ArrayList<>();
    public Response addOutPut(OutputDto outputDto) {
        Output output = new Output();
         OutputProduct outputProduct=new OutputProduct();
//        for (int i = 0; i < outputDto.getOutputProductDtoList().size(); i++) {
//            OutputProductDto outputProductDto = outputDto.getOutputProductDtoList().get(i);
//            outputProduct.setAmount(outputProductDto.getAmount());
//         outputProduct.setPrice(outputProductDto.getPrice());
//
//         Product product = productRepository.findById(outputProductDto.getProductId()).get();
//         outputProduct.setProduct(product);
//
//         OutputProduct outputProduct1 = outPutProductRepository.save(outputProduct);
//         productList.add(outputProduct1);
//        }
        Optional<Client> client = clientRepository.findById(outputDto.getClientId());
        Warehouse warehouse = warehouseRepository.getById(outputDto.getWarehouseId());
        Optional<Currency> currency = currencyRepository.findById(outputDto.getCurrencyId());
       // output.setOutputProduct(productList);
        output.setCurrency(currency.get());
        output.setClient(client.get());
        output.setWarehouse(warehouse);
        outPutRepository.save(output);

        return new Response("Added",true);

    }
     public List<OutputProduct>getAllOutPutProduct(){
        return outPutProductRepository.findAll();
     }
    public List<Currency> getAllCurrency() {
        return  currencyRepository.findAll();
    }

    public Output getByIdi(Integer id) {
        return outPutRepository.findById(id).orElse(new Output());
    }

    public Response edit(OutputDto outputDto,Integer id) {
        Output output = outPutRepository.findById(id).get();
        Optional<Client> client = clientRepository.findById(outputDto.getClientId());
        Warehouse warehouse = warehouseRepository.getById(outputDto.getWarehouseId());
        Optional<Currency> currency = currencyRepository.findById(outputDto.getCurrencyId());

        output.setClient(client.get());
        output.setCurrency(currency.get());
        output.setWarehouse(warehouse);
        outPutRepository.save(output);
         return new Response("Edited",true);
    }


    public void addOutPutPro(OutputProductDto outputProductDto) {
        OutputProduct outputProduct = new OutputProduct();
        Product product = productRepository.getById(outputProductDto.getProductId());
        outputProduct.setProduct(product);
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outPutProductRepository.save(outputProduct);
        productList.add(outputProduct.getId());
        System.out.println(productList);
    }
}
