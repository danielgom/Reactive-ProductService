package com.reactivestack.productservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
}
