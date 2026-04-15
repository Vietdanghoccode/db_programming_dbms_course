package com.example.sakila.service;

import com.example.sakila.entity.Customer;
import com.example.sakila.entity.Product;
import com.example.sakila.entity.Order;
import com.example.sakila.repository.CustomerRepository;
import com.example.sakila.repository.ProductRepository;
import com.example.sakila.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Map<String, Object>> getRevenueByCustomer() {
        return customerRepository.getRevenueByCustomer().stream()
            .map(obj -> {
                Map<String, Object> map = new HashMap<>();
                map.put("customer", obj[0] != null ? obj[0] : "Unknown");
                map.put("total", obj[1] != null ? obj[1] : 0.0);
                return map;
            })
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getTopDebtors() {
        return customerRepository.getTopDebtors().stream()
            .map(obj -> {
                Map<String, Object> map = new HashMap<>();
                map.put("customer", obj[0] != null ? obj[0] : "Unknown");
                map.put("debt", obj[1] != null ? obj[1] : 0.0);
                return map;
            })
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRevenueByProduct() {
        return productRepository.getRevenueByProduct().stream()
            .map(obj -> {
                Map<String, Object> map = new HashMap<>();
                map.put("product", obj[0] != null ? obj[0] : "Unknown");
                map.put("total", obj[1] != null ? obj[1] : 0.0);
                return map;
            })
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRevenueByMonth() {
        return orderRepository.getRevenueByMonth().stream()
            .map(obj -> {
                Map<String, Object> map = new HashMap<>();
                map.put("month", obj[0] != null ? obj[0] : "Unknown");
                map.put("total", obj[1] != null ? obj[1] : 0.0);
                return map;
            })
            .collect(Collectors.toList());
    }

    public List<Customer> searchCustomers(String name) {
        return customerRepository.findByCustomerNameContainingIgnoreCase(name);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public List<Order> searchOrders(String start, String end) {
        return orderRepository.findByOrderDateBetween(LocalDate.parse(start), LocalDate.parse(end));
    }
}
