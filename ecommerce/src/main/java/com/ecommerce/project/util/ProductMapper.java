package com.ecommerce.project.util;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toProduct(ProductDTO productDTO) {
        if (productDTO == null) return null;
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<ProductDTO> toDTOList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductResponse toProductResponse(List<ProductDTO> productDTOList) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        return productResponse;
    }

    public ProductResponse toProductResponse(List<ProductDTO> productDTOList,
                                             Page<Product> pageProducts) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOList);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    public void updateProductFromModel(Product source, Product target) {
        modelMapper.map(source, target);
    }
}
