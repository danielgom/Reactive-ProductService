package com.reactivestack.productservice.controller;

import com.reactivestack.productservice.dto.ProductDto;
import com.reactivestack.productservice.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDto>> getProductByNameOrId(@RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String id) {
        return this.productService.getByNameOrId(name, id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getAll() {
        return this.productService.getAll();
    }

    @GetMapping(value = "/price-range", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getByPriceRange(@RequestParam Double min,
                                            @RequestParam Double max) {
        return this.productService.getAllByPriceRange(min, max);
    }

    @PostMapping("/")
    public Mono<ProductDto> createProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.insertProduct(productDtoMono);
    }

    @PutMapping("/")
    public Mono<ResponseEntity<ProductDto>> fullUpdate(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.updateProduct(productDtoMono)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return this.productService.deleteProductById(id);
    }
}
