package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryContoller {

    private final CategoryServiceImplementation categoryServiceImplementation;

    public CategoryContoller(
            CategoryServiceImplementation categoryServiceImplementation) {
        this.categoryServiceImplementation = categoryServiceImplementation;
    }

//    @GetMapping("/api/public/categories")
    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryServiceImplementation.getAllCategories());
    }

//    @PostMapping("/api/public/categories")
    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryServiceImplementation.createCategory(category);
        return ResponseEntity.ok("Category created successfully!");
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryServiceImplementation.deleteCategory(categoryId);
//            return ResponseEntity.ok(status);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long categoryId){
        Category updatedCategory = categoryServiceImplementation.updateCategory(category, categoryId);
//            return ResponseEntity.ok(status);
        return ResponseEntity.status(HttpStatus.OK).body("Category with ID: "+updatedCategory.getCategoryId());
    }
}
