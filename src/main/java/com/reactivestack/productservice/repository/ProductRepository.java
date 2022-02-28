package com.reactivestack.productservice.repository;

import com.reactivestack.productservice.entity.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByNameOrId(String name, String id);

    Mono<Product> findByName(String name);

    @Query(value = "{'price' : {$gte : ?0, $lte : ?1}}" )
    Flux<Product> findAllByPriceBetweenQuery(Double lowerBoundary, Double higherBoundary);
}
