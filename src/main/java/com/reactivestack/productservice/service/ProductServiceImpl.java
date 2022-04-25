package com.reactivestack.productservice.service;

import com.reactivestack.productservice.dto.ProductDto;
import com.reactivestack.productservice.exceptions.BadRequestException;
import com.reactivestack.productservice.exceptions.NotFoundException;
import com.reactivestack.productservice.mapper.ProductMapper;
import com.reactivestack.productservice.mapper.ProductMapperImpl;
import com.reactivestack.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = new ProductMapperImpl();

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll()
                .map(this.productMapper::toDto);
    }

    @Override
    public Mono<ProductDto> getByNameOrId(String name, String id) {

        if (!StringUtils.hasText(name) && !StringUtils.hasText(id)) {
            return Mono.error(new BadRequestException("please provide name or Id to find the product"));
        }

        return this.productRepository.findByNameOrId(name, id)
                .switchIfEmpty(Mono.error(() -> {
                    if (!StringUtils.hasText(name)) {
                        return new NotFoundException(String.format("product with id %s not found", id));
                    }
                    return new NotFoundException(String.format("product with name %s not found", name));
                }))
                .map(this.productMapper::toDto);
    }

    @Override
    public Flux<ProductDto> getAllByPriceRange(Double lowerBound, Double upperBound) {
        return this.productRepository.findAllByPriceBetweenQuery(lowerBound, upperBound)
                .map(this.productMapper::toDto);
    }

    @Override
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(productMapper::toEntity)
                .flatMap(this.productRepository::save)
                .map(productMapper::toDto);
    }

    @Override
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .flatMap(productDto -> this.productRepository.findByName(productDto.getName())
                        .flatMap(product -> {
                            product.setName(productDto.getName());
                            product.setDescription(productDto.getDescription());
                            product.setPrice(productDto.getPrice());
                            return this.productRepository.save(product);
                        })
                )
                .map(this.productMapper::toDto);
    }

    @Override
    public Mono<Void> deleteProductById(String id) {
        System.out.println();
        return this.productRepository.deleteById(id);
    }

}
