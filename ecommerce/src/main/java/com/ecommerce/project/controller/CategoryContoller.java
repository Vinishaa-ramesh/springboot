package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryContoller {

    private final CategoryServiceImplementation categoryServiceImplementation;

    public CategoryContoller(
            CategoryServiceImplementation categoryServiceImplementation) {
        this.categoryServiceImplementation = categoryServiceImplementation;
    }

    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name="message", required = false) String message) {
        return new ResponseEntity<>("Echoed message: "+ message, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategories(
                @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
                @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY) String sortBy,
                @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        return ResponseEntity.ok(categoryServiceImplementation.getAllCategories(pageNo, pageSize, sortBy, sortOrder));
    }

    @RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryServiceImplementation.createCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO savedCategory = categoryServiceImplementation.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(savedCategory);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category,
                                                 @PathVariable Long categoryId){
        CategoryDTO updatedCategory = categoryServiceImplementation.updateCategory(category, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }
}
