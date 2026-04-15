package com.example.sakila.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private Integer orderNumber;
    private LocalDate orderDate;
    private LocalDate shippedDate;
    private String status;
    private Integer customerNumber;
}
