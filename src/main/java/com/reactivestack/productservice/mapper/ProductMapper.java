package com.reactivestack.productservice.mapper;

import com.reactivestack.productservice.dto.ProductDto;
import com.reactivestack.productservice.entity.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "name", target = "name")
    ProductDto toDto(Product product);

    @InheritInverseConfiguration
    Product toEntity(ProductDto productDto);

}
