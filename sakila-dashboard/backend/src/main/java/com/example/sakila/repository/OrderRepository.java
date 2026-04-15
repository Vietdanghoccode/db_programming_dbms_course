package com.example.sakila.repository;

import com.example.sakila.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOrderDateBetween(LocalDate start, LocalDate end);

    @Query(value = "SELECT DATE_FORMAT(paymentDate, '%Y-%m') as month, SUM(amount) as total FROM payments GROUP BY month ORDER BY month", nativeQuery = true)
    List<Object[]> getRevenueByMonth();
}
