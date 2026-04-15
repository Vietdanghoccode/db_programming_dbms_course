package com.example.sakila.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    private String productCode;
    private String productName;
    private String productLine;
    private Double buyPrice;
    private Integer quantityInStock;
}
