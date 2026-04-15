package com.example.sakila.repository;

import com.example.sakila.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerNameContainingIgnoreCase(String name);

    @Query(value = "SELECT c.customerName, SUM(p.amount) as total FROM customers c JOIN payments p ON c.customerNumber = p.customerNumber GROUP BY c.customerName ORDER BY total DESC LIMIT 10", nativeQuery = true)
    List<Object[]> getRevenueByCustomer();
}
