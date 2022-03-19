package uz.pdp.warehouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.dto.SupplierDTO;
import uz.pdp.warehouseapp.entity.Client;
import uz.pdp.warehouseapp.entity.Supplier;
import uz.pdp.warehouseapp.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getOne(Integer id) {

        Optional<Supplier> byId = supplierRepository.findById(id);
        if (!byId.isPresent()) {
            return new Supplier();
        }
        return byId.get();
    }

//    public Response edit(Integer id, Supplier supplier) {
//
//        Optional<Supplier> byId = supplierRepository.findById(id);
//
//        Supplier editSupplier = byId.get();
//
//        if (!byId.isPresent()) {
//            return new Response("This supplier not found(id)", false);
//        }
//        editSupplier.setName(supplier.getName());
//        editSupplier.setPhoneNumber(supplier.getPhoneNumber());
//        supplierRepository.save(editSupplier);
//        return new Response("Successfully edited", true);
//    }

    public Response edit(Supplier supplier) {
        Response response = new Response();
        boolean hasNumber = false;
        for (Supplier supplier1 : supplierRepository.findAll()) {
            if (supplier1.getPhoneNumber().equals(supplier.getPhoneNumber()) &&
                    !supplier1.getId().equals(supplier.getId())) {
                hasNumber = true;
            }
        }
        if (!hasNumber) {
            supplierRepository.save(supplier);
            response.setSuccess(true);
            response.setMessage("Edite supplier");
            return response;
        }
        response.setMessage("This supplier number already exist");
        return response;
    }


    public Response add(SupplierDTO supplierDTO) {

        Supplier supplier1 = new Supplier();

        supplier1.setName(supplierDTO.getName());
        supplier1.setPhoneNumber(supplierDTO.getPhoneNumber());
        supplierRepository.save(supplier1);
        return new Response("New supplier added", true);
    }

    public Response delete(Integer id) {

        Optional<Supplier> byId = supplierRepository.findById(id);

        if (!byId.isPresent()) {
            return new Response("This id not exists", false);
        }
        supplierRepository.deleteById(id);
        return new Response("Deleted", true);
    }

    public List<Supplier> getOnlyActive() {
        return supplierRepository.getOnlyActive();
    }
}
