package uz.pdp.warehouseapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouseapp.dto.CategoryDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Category;
import uz.pdp.warehouseapp.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/warehouse/category")
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showCategory(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        List<Category> categories = categoryService.getAllCategory();
        List<Category> collect = categories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("categories", collect);
        List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categoriesChoose", chooseList);


        if (categories.isEmpty()) {
            model.addAttribute("message", new Response("Not found any category", false));
        } else
            model.addAttribute("message", new Response("Total category amount: " + categories.size(), true));
        return "/category/categoryOperation";
    }

    @PostMapping(path = "/add")
    public String addCategory(CategoryDto categoryDto, Model model) {
        Response response = categoryService.addCategory(categoryDto);
        model.addAttribute("categoryDto", new CategoryDto());
        List<Category> categories = categoryService.getAllCategory();
        List<Category> collect = categories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("categories", collect);
        List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categoriesChoose", chooseList);
        model.addAttribute("message", response);
        return "/category/categoryOperation";
    }

    @GetMapping(path = "/edite/{id}")
    public String editeCategory(@PathVariable Integer id, Model model) {

        Category category = categoryService.getCategoryByID(id);
        List<Category> categories = categoryService.getAllCategory();
        List<Category> collect = categories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        model.addAttribute("categories", collect);
        List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
        chooseList.remove(category);
        model.addAttribute("categoriesChoose", chooseList);
        if (categories.isEmpty()) {

            model.addAttribute("message", new Response("Not found this category", false));
        } else {
            model.addAttribute("category", category);
        }
        model.addAttribute("message", new Response());
        return "/category/editeCategory";
    }

    @PostMapping(path = "/edite/{id}")
    public String updateCategory(Category category, Model model) {
        Response response = categoryService.updateCategory(category);
        if (response.isSuccess()) {
            model.addAttribute("categoryDto", new CategoryDto());
            List<Category> categories = categoryService.getAllCategory();
            List<Category> collect = categories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            model.addAttribute("categories", collect);
            List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
            chooseList.remove(category);
            model.addAttribute("categoriesChoose", chooseList);
            model.addAttribute("message", response);

            return "/category/categoryOperation";
        }
        Category categoryReturn = categoryService.getCategoryByID(category.getId());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categoriesChoose", chooseList);

        model.addAttribute("message", response);
        if (categories.isEmpty()) {

            model.addAttribute("message", new Response("Not found this category", false));
        } else {
            model.addAttribute("category", categoryReturn);
        }
        return "/category/editeCategory";
    }
     @GetMapping(path = "/delete/{id}")
    public String deleteCategory(Category category, Model model) {
        Response response = categoryService.deleteCategory(category.getId());
        if (response.isSuccess()) {
            model.addAttribute("categoryDto", new CategoryDto());
            List<Category> categories = categoryService.getAllCategory();
            List<Category> collect = categories.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
            model.addAttribute("categories", collect);
            List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
            chooseList.remove(category);
            model.addAttribute("categoriesChoose", chooseList);
            model.addAttribute("message", response);

            return "/category/categoryOperation";
        }
        Category categoryReturn = categoryService.getCategoryByID(category.getId());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        List<Category> chooseList = categories.stream().filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categoriesChoose", chooseList);

        model.addAttribute("message", response);
        if (categories.isEmpty()) {

            model.addAttribute("message", response);
        } else {
            model.addAttribute("category", categoryReturn);
        }
        return "/category/editeCategory";
    }
}
