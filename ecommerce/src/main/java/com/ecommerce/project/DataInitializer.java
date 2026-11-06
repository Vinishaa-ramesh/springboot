package com.ecommerce.project;

import com.ecommerce.project.data.repositories.CategoryRepository;
import com.ecommerce.project.data.repositories.ProductRepository;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return (args -> {
            Category category1 = new Category();
            category1.setCategoryName("Sports and Fitness");
            categoryRepository.save(category1);

            Category category2 = new Category();
            category2.setCategoryName("Electronics");
            categoryRepository.save(category2);

            Product product1 = new Product();
            product1.setProductName("Adjustable dumbbell set for home workouts | Premium Quality");
            product1.setDescription("Adjustable dumbbell set for home workouts, can be used indoors, outdoors, at your personal gym. This is available at lowest possible rates.");
            product1.setQuantity(90);
            product1.setPrice(90D);
            product1.setDiscount(90D);
            product1.setSpecialPrice(10D);
            product1.setImage("default.png");
            product1.setCategory(category1);

            productRepository.save(product1);
        });
    }
}
