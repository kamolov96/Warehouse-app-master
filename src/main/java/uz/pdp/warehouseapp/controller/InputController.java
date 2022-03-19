package uz.pdp.warehouseapp.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.InputDto;
import uz.pdp.warehouseapp.dto.InputProductDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.*;
import uz.pdp.warehouseapp.service.*;
import uz.pdp.warehouseapp.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
@RequestMapping(path = "/warehouse/input")
@RequiredArgsConstructor
public class InputController {
final WarehouseServise warehouseServise;
final SupplierService supplierService;
final ProductService productService;
final CurrencyServer currencyServer;
final InputService inputService;
    @GetMapping
    public String inputPage(Model model){
        List<Product> allProduct = productService.getAllProduct();
        if(allProduct.isEmpty()){
            return "redirect:/warehouse/product";
        }
        List< Warehouse>warehouses= warehouseServise.getAllActive();
        if(warehouses.isEmpty()){
            return "redirect:/warehouse";
        }
        model.addAttribute("warehouses",warehouses);
        List<Supplier>suppliers=supplierService.getOnlyActive();
        if(suppliers.isEmpty()){
            return "redirect:/warehouse/supplier";
        }
        model.addAttribute("suppliers",suppliers);
             List<Currency>currencies=currencyServer.getOnlyActive();
        if(currencies.isEmpty()){
            return "redirect:/warehouse/currency";
        }
        model.addAttribute("currencies",currencies);
        model.addAttribute("message",new Response());
        return "/input/inputAdd";
    }
    @PostMapping
    public String saveInput(@ModelAttribute InputDto inputDto,Model model){
        Input input =inputService.saveInput(inputDto);
        model.addAttribute("input",input);
        model.addAttribute("message",new Response("Add input ",true));
        model.addAttribute("inputProductDto",new InputProductDto());
        List<Product> allProduct = productService.getAllProduct();

        model.addAttribute("products",allProduct);
        return "/input/inputProduct";
    }
    @GetMapping(path = "/{id}")
    public String input(@PathVariable Integer id, Model model) {
        Input input = inputService.getInput(id);
        List<InputProduct> inputProductList = input.getInputProductList();
        List<Product> collect = inputProductList.stream().map(InputProduct::getProduct).collect(Collectors.toList());
        List<Integer> indedx = collect.stream().map(Product::getId).collect(Collectors.toList());
        model.addAttribute("input", input);
        model.addAttribute("message", new Response());
        List<Product> allProduct = productService.getAllProduct();
        allProduct.removeIf(product -> indedx.contains(product.getId()));
        model.addAttribute("products",allProduct);
        return "/input/inputProduct";
    }
     @PostMapping(path = "/{id}/product")
    public String saveProduct(@PathVariable Integer id,InputProductDto inputProductDto, Model model) {
        Input input = inputService.saveProduct(id,inputProductDto);
        if(Objects.isNull(input.getId())){
            input = inputService.getInput(id);
            model.addAttribute("message", new Response("You entered wrong expire date ",false));
        }else {
             model.addAttribute("message", new Response("Add inputProduct ",true));
        }
 List<InputProduct> inputProductList = input.getInputProductList();
        List<Product> collect = inputProductList.stream().map(InputProduct::getProduct).collect(Collectors.toList());
        List<Integer> indedx = collect.stream().map(Product::getId).collect(Collectors.toList());
        model.addAttribute("input", input);
         List<Product> allProduct = productService.getAllProduct();
        allProduct.removeIf(product -> indedx.contains(product.getId()));
        model.addAttribute("products",allProduct);
        return "/input/inputProduct";
    }
    @GetMapping(path = "/show")
    public String showInputs(Model model,@RequestParam(value = "page",defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
                              @RequestParam(value = "size",defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size){
           if(page>0){
            page=page-1;
        }
         Page<Input> inputs=inputService.getAll(PageRequest.of(page,size));
                int totalPages = inputs.getTotalPages();
        if(totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
        }else {
            List<Integer> pageNumbers = new ArrayList<>();
            model.addAttribute("pageNumbers",pageNumbers);
        }
        if(inputs.isEmpty()){
            model.addAttribute("message", new Response("Not found any input",false));
        }else {
             model.addAttribute("message", new Response());
        }
        List<Input> content =inputs.getContent();
        model.addAttribute("inputs",content);
        int pageNumber = inputs.getPageable().getPageNumber();
        model.addAttribute("currentPage",pageNumber);

        return "/input/showInputs";
    }
    @GetMapping(path = "/{id}/products")
    public String infoInput(Model model,@PathVariable Integer id,
                            @RequestParam(value = "page",defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
                            @RequestParam(value = "size",defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size){
        if(page>0)
            page=page-1;
        Page<InputProduct>inputProducts=inputService.getInputProduct(PageRequest.of(page,size),id);
                  int totalPages = inputProducts.getTotalPages();
        if(totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
        }else {
            List<Integer> pageNumbers = new ArrayList<>();
            model.addAttribute("pageNumbers",pageNumbers);
        }
         if(inputProducts.isEmpty()){
            model.addAttribute("message", new Response("Not found any input",false));
        }else {
             model.addAttribute("message", new Response());
        }
        List<InputProduct> content = inputProducts.getContent();
        model.addAttribute("inputProducts",content);
        model.addAttribute("input",inputService.getInput(id));
        int pageNumber = inputProducts.getPageable().getPageNumber();
        model.addAttribute("currentPage",pageNumber);
        return "/input/infoInput";
    }
}
