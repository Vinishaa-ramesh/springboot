package com.ecommerce.project.service;

import com.ecommerce.project.data.repositories.CategoryRepository;
import com.ecommerce.project.data.repositories.ProductRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.util.ProductMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public ProductServiceImplementation(ProductRepository productRepository,
                                        CategoryRepository categoryRepository,
                                        ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = new ProductMapper(modelMapper);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Product product = productMapper.toProduct(productDTO);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

        List<Product> productsList = category.getProducts();
        boolean isProductPresent = productsList.stream()
                .anyMatch(productSub -> productSub.getProductName().equals(product.getProductName()));

        if(isProductPresent)
            throw new APIException("Product already exists");

        product.setImage("default.png");
        product.setCategory(category);
        Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageDetails(pageNo, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> productList = productPage.getContent();

        if(productList.isEmpty())
            throw new APIException("No Products present");

        List<ProductDTO> productDTOList = productMapper.toDTOList(productList);
        return productMapper.toProductResponse(productDTOList, productPage);
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId,
                                                 Integer pageNo,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

        Pageable pageDetails = getPageDetails(pageNo, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);
        List<Product> productList = productPage.getContent();

        if(productList.isEmpty())
            throw new APIException("No Products present with filter");

        List<ProductDTO> productDTOList = productMapper.toDTOList(productList);
        return productMapper.toProductResponse(productDTOList, productPage);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageDetails(pageNo, pageSize, sortBy, sortOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%', pageDetails);
        List<Product> productList = productPage.getContent();

        if(productList.isEmpty())
            throw new APIException("No Products present with filter");

        List<ProductDTO> productDTOList = productMapper.toDTOList(productList);
        return productMapper.toProductResponse(productDTOList, productPage);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productMapper.toProduct(productDTO);
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product_id", productId));
        product.setProductId(productId);
        Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        productMapper.updateProductFromModel(product, productFromDb);
        Product savedProduct = productRepository.save(productFromDb);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product_id", productId));
        productRepository.delete(productFromDb);
        return productMapper.toDTO(productFromDb);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product_id", productId));
        String fileName = fileService.uploadFile(path, image);
        productFromDb.setImage(fileName);
        Product updatedProduct = productRepository.save(productFromDb);
        return productMapper.toDTO(updatedProduct);
    }

    private Pageable getPageDetails(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNo, pageSize, sortByAndOrder);
    }
}
