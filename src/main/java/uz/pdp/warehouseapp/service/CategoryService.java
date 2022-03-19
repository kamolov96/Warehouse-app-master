package uz.pdp.warehouseapp.service;

import org.springframework.stereotype.Service;
import uz.pdp.warehouseapp.dto.CategoryDto;
import uz.pdp.warehouseapp.dto.Response;
import uz.pdp.warehouseapp.entity.Category;
import uz.pdp.warehouseapp.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Response addCategory(CategoryDto categoryDto) {

        Response response=new Response();
        boolean categoryByName = findCategoryByName(categoryDto.getName());
        if(!categoryByName) {
            if (!categoryDto.getParentCategoryId().equals(-1)) {
                if (categoryDto.getParentCategoryId().equals(0)) {
                    Category category = new Category(categoryDto.getName(), categoryDto.isActive());
                    categoryRepository.save(category);
                }else {
                    Category category=new Category(categoryDto.getName(), categoryRepository.getById(categoryDto.getParentCategoryId()),categoryDto.isActive());
                    categoryRepository.save(category);
                }
                response.setSuccess(true);
                response.setMessage("Add category");
                return response;
            }
            response.setMessage("Please choose parent category");
            return response;
        }
        response.setMessage("This name already exist");
        return response;
    }
  public boolean findCategoryByName(String name){
      for (Category category : categoryRepository.findAll()) {
          if(category.getName().trim().equalsIgnoreCase(name.trim()))
              return true;
      }
      return false;
  }

    public Category getCategoryByID(Integer id) {
        return categoryRepository.findById(id).orElse(new Category());
    }
    public Response updateCategory(Category category) {
        Response response=new Response();
        boolean hasName=false;
        for (Category category1 : categoryRepository.findAll()) {
            if (category1.getName().trim().equalsIgnoreCase(category.getName().trim()) && !category1.getId().equals(category.getId())) {
                hasName = true;
                break;
            }
        }
        if(!hasName) {
            categoryRepository.save(category);
            response.setSuccess(true);
            response.setMessage("Edite category");
            return response;
        }
        response.setMessage("This name already exist");
        return response;
    }

    public Response deleteCategory(Integer id) {
        Response response=new Response();
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            category.setActive(false);
            categoryRepository.save(category);
            response.setMessage("This category is no active");
            response.setSuccess(true);
            return response;
        }
        response.setMessage("Not found category");
        return response;
    }
}
