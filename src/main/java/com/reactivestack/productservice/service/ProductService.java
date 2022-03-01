package com.reactivestack.productservice.service;

import com.reactivestack.productservice.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getAll();

    Mono<ProductDto> getByNameOrId(String name, String id);

    Flux<ProductDto> getAllByPriceRange(Double upperBound, Double lowerBound);

    Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono);

    Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono);

    Mono<Void> deleteProductById(String id);

}
