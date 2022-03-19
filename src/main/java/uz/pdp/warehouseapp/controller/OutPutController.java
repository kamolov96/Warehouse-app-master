package uz.pdp.warehouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.OutputDto;
import uz.pdp.warehouseapp.dto.OutputProductDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.*;
import uz.pdp.warehouseapp.repository.OutPutRepository;
import uz.pdp.warehouseapp.repository.ProductRepository;
import uz.pdp.warehouseapp.service.ClientService;
import uz.pdp.warehouseapp.service.OutPutService;
import uz.pdp.warehouseapp.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/warehouse/output")
public class OutPutController {
    @Autowired
    ProductService productService;
    @Autowired
    OutPutRepository outPutRepository;
    @Autowired
    OutPutService outPutService;
    @Autowired
    ClientService clientService;

    @GetMapping()
    public String showOutPut(Model model) {
        model.addAttribute("outPutProductDto",new OutputProductDto());

        model.addAttribute("outPutDto", new OutputDto());
        List<Client> clients = clientService.getAll();
        List<Product> products = productService.getAllProduct();

        model.addAttribute("products", products);
        model.addAttribute("clients", clients);

        List<Warehouse> warehouses = outPutService.getAllWarehouse();
        model.addAttribute("warehouses", warehouses);

        List<Currency> currencies = outPutService.getAllCurrency();
        model.addAttribute("currencies", currencies);

        List<Output> outputs = outPutService.getAll();
        model.addAttribute("outputs", outputs);
        if (outputs.isEmpty()) {
            model.addAttribute("message", new Response("Not found any client", false));
        } else
            model.addAttribute("message", new Response("Total client amount: " + outputs.size(), true));
        return "/output/outputOperation";
    }
      @GetMapping("/addOPP")
      public String addOutPutPro(Model model ,OutputProductDto outputProductDto){

        outPutService.addOutPutPro(outputProductDto);
          return "redirect:/output/outputOperation";
      }
    @PostMapping(path = "/add")
    public String addCategory(OutputDto outputDto, Model model) {
        Response response = outPutService.addOutPut(outputDto);

        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);

        List<Warehouse> warehouses = outPutService.getAllWarehouse();
        model.addAttribute("warehouses", warehouses);

        List<Currency> currencies = outPutService.getAllCurrency();
        model.addAttribute("currencies", currencies);

        List<Output> outputs = outPutRepository.findAll();
        model.addAttribute("output", outputs);

        List<Product> products = productService.getAllProduct();
        model.addAttribute("products",products);

        List<OutputProduct> allOutPutProduct = outPutService.getAllOutPutProduct();
        model.addAttribute("outPutProList",allOutPutProduct);

        model.addAttribute("massage", response);
        return "redirect:/warehouse/output";
    }


    @GetMapping("/edite/{id}")
    public String editeOutPut(@PathVariable Integer id, Model model) {
        Output output = outPutService.getByIdi(id);
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        List<Warehouse> warehouses = outPutService.getAllWarehouse();
        model.addAttribute("warehouses", warehouses);
        List<Currency> currencies = outPutService.getAllCurrency();
        model.addAttribute("currencies", currencies);
        List<Output> outputs = outPutService.getAll();
        List<Output> outputList = outputs.stream().sorted((o1, o2) -> o1.getClient().getName().compareTo(o2.getClient().getName())).
                collect(Collectors.toList());
        model.addAttribute("output", output);
        model.addAttribute("outputList", outputList);
        if (outputs.isEmpty()) {
            model.addAttribute("message", new Response("Not found any client", false));
        } else
            model.addAttribute("message", new Response("Total client amount: " + outputs.size(), true));
        return "/output/outputEdite";
    }

    @PostMapping("/edite/{id}")
    public String editeOut(OutputDto output, Model model, @PathVariable Integer id) {
        Response response = outPutService.edit(output, id);
        List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        List<Warehouse> warehouses = outPutService.getAllWarehouse();
        model.addAttribute("warehouses", warehouses);
        List<Currency> currencies = outPutService.getAllCurrency();
        model.addAttribute("currencies", currencies);
        List<Output> outputs = outPutService.getAll();
//        List<Output> outputList = outputs.stream().sorted((o1, o2) -> o1.getClient().getName().compareTo(o2.getClient().getName())).
//                collect(Collectors.toList());
        model.addAttribute("outputList", outputs);
        return "redirect:/warehouse/output";
    }

}
