package com.example.sakila.repository;

import com.example.sakila.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String name);

    @Query(value = "SELECT p.productName, SUM(od.quantityOrdered * od.priceEach) as total FROM products p JOIN orderdetails od ON p.productCode = od.productCode GROUP BY p.productName ORDER BY total DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getRevenueByProduct();
}
