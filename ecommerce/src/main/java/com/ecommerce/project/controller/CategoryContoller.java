package com.ecommerce.project.controller;

import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryServiceImplementation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryContoller {

    private final CategoryServiceImplementation categoryServiceImplementation;
    private final ModelMapper modelMapper;

    public CategoryContoller(
            CategoryServiceImplementation categoryServiceImplementation, ModelMapper modelMapper) {
        this.categoryServiceImplementation = categoryServiceImplementation;
        this.modelMapper = modelMapper;
    }

//    @GetMapping("/api/public/categories")
    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategories() {
        return ResponseEntity.ok(categoryServiceImplementation.getAllCategories());
    }

//    @PostMapping("/api/public/categories")
    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryServiceImplementation.createCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO savedCategory = categoryServiceImplementation.deleteCategory(categoryId);
//            return ResponseEntity.ok(status);
        return ResponseEntity.status(HttpStatus.OK).body(savedCategory);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category,
                                                 @PathVariable Long categoryId){
        CategoryDTO updatedCategory = categoryServiceImplementation.updateCategory(category, categoryId);
//            return ResponseEntity.ok(status);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }
}
