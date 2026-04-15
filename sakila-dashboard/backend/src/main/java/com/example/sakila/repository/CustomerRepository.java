package com.example.sakila.repository;

import com.example.sakila.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerNameContainingIgnoreCase(String name);

    @Query(value = "SELECT c.customerName, SUM(p.amount) as total FROM customers c JOIN payments p ON c.customerNumber = p.customerNumber GROUP BY c.customerName ORDER BY total DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getRevenueByCustomer();

    @Query(value = "SELECT c.customerName, \n" +
            "       (SELECT COALESCE(SUM(od.quantityOrdered * od.priceEach), 0) FROM orders o JOIN orderdetails od ON o.orderNumber = od.orderNumber WHERE o.customerNumber = c.customerNumber) \n" +
            "       - (SELECT COALESCE(SUM(p.amount), 0) FROM payments p WHERE p.customerNumber = c.customerNumber) as debt \n" +
            "FROM customers c \n" +
            "HAVING debt > 0 \n" +
            "ORDER BY debt DESC \n" +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> getTopDebtors();
}
