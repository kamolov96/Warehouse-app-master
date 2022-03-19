package uz.pdp.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.dto.SupplierDTO;
import uz.pdp.warehouseapp.entity.Supplier;
import uz.pdp.warehouseapp.service.SupplierService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/warehouse/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("supplierDTO", new SupplierDTO());
        List<Supplier> supplier = supplierService.getAll();
        List<Supplier> suppliers = supplier.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());
        model.addAttribute("suppliers", suppliers);
        List<Supplier> chooseList = supplier.stream().filter(Supplier::isActive).collect(Collectors.toList());
        model.addAttribute("supplierChoose", chooseList);


        if (suppliers.isEmpty()) {
            model.addAttribute("message", new Response("Not found any supplier", false));
        } else
            model.addAttribute("message", new Response("Total supplier amount: " + suppliers.size(), true));
        return "/sirojiddin/sup";
    }

    @GetMapping("/{id}")
    public Supplier getOne(@PathVariable Integer id) {
        return supplierService.getOne(id);
    }

//    @PutMapping("/edit/{id}")
//    public Response edit(@PathVariable Integer id, Supplier supplier) {
//        return supplierService.edit(id, supplier);
//    }

    @GetMapping(path = "/edit/{id}")
    public String editeSupplier(@PathVariable Integer id, Model model) {

        Supplier supplier = supplierService.getOne(id);
        List<Supplier> suppliers = supplierService.getAll();
        List<Supplier> collect = suppliers.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("suppliers", collect);
        List<Supplier> chooseList = suppliers.stream().filter(Supplier::isActive).collect(Collectors.toList());
        chooseList.remove(supplier);
        model.addAttribute("supplierChoose", chooseList);
        if (suppliers.isEmpty()) {
            model.addAttribute("message", new Response("Not found this supplier", false));
        } else {
            model.addAttribute("supplier", supplier);
        }
        model.addAttribute("message", new Response());
        return "/sirojiddin/supplierEdit";
    }

    @PostMapping(path = "/edit/{id}")
    public String updateSupplier(Supplier supplier, Model model) {
        Response response = supplierService.edit(supplier);
        if (response.isSuccess()) {
            model.addAttribute("supplierDTO", new SupplierDTO());
            List<Supplier> suppliers = supplierService.getAll();
            List<Supplier> collect = suppliers.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            model.addAttribute("suppliers", collect);
            List<Supplier> chooseList = suppliers.stream().filter(Supplier::isActive).collect(Collectors.toList());
            chooseList.remove(supplier);
            model.addAttribute("supplierChoose", chooseList);
            model.addAttribute("message", response);
            return "redirect:/warehouse/supplier";
        }
        Supplier supplierReturn = supplierService.getOne(supplier.getId());
        List<Supplier> suppliers = supplierService.getAll();
        List<Supplier> collect = suppliers.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("suppliers", collect);
        List<Supplier> chooseList = suppliers.stream().filter(Supplier::isActive).collect(Collectors.toList());
        chooseList.remove(supplier);
        model.addAttribute("suppliersChoose", chooseList);
        if (suppliers.isEmpty()) {
            model.addAttribute("message", new Response("Not found this supplier", false));
        } else {
            model.addAttribute("client", supplierReturn);
        }
        model.addAttribute("message", new Response());
        return "/sirojiddin/supplierEdit";
    }

    @PostMapping(path = "/add")
    public String add(SupplierDTO supplierDTO, Model model) {
        Response response = supplierService.add(supplierDTO);
        model.addAttribute("supplierDTO", new SupplierDTO());
        List<Supplier> suppliers = supplierService.getAll();
        List<Supplier> collect = suppliers.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("suppliers", collect);
        List<Supplier> chooseList = suppliers.stream().filter(Supplier::isActive).collect(Collectors.toList());
        model.addAttribute("supplierChoose", chooseList);
        model.addAttribute("message", response);
        return "/sirojiddin/sup";
    }
//    @GetMapping(path = "/add/1")
//    public String add( Model model) {
//        model.addAttribute("supplierDTO", new SupplierDTO());
////        List<Supplier> suppliers = supplierService.getAll();
////        List<Supplier> collect = suppliers.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
////        model.addAttribute("suppliers", collect);
////        List<Supplier> chooseList = suppliers.stream().filter(Supplier::isActive).collect(Collectors.toList());
////        model.addAttribute("supplierChoose", chooseList);
//        model.addAttribute("message", new Response());
//        return "/sirojiddin/sup";
//    }
    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id) {
        return supplierService.delete(id);
    }

}
