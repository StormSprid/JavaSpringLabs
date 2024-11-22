package stormsprid.emilspring.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import stormsprid.emilspring.Entity.Category;
import stormsprid.emilspring.Repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }
}
