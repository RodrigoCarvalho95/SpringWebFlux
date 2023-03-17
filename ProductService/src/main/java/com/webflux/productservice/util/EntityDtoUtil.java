package com.webflux.productservice.util;

import org.springframework.beans.BeanUtils;

import com.webflux.productservice.dto.ProductDto;
import com.webflux.productservice.entity.Product;

public class EntityDtoUtil {
	
    public static ProductDto toDto(Product product){
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

}
