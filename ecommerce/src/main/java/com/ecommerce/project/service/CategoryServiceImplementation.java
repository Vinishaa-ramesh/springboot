package com.ecommerce.project.service;

import com.ecommerce.project.data.repositories.CategoryRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No categories found!");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb!=null)
            throw new APIException("Category with name "+ category.getCategoryName()+" already exists");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        categoryRepository.delete(savedCategory);
        CategoryDTO deletedCategory = modelMapper.map(savedCategory, CategoryDTO.class);
        return deletedCategory;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,
                                   Long categoryId){
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

        Category inputCategory = modelMapper.map(categoryDTO, Category.class);
        inputCategory.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(inputCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
