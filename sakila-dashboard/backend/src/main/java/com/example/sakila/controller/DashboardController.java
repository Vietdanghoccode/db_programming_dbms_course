package com.example.sakila.controller;

import com.example.sakila.entity.Customer;
import com.example.sakila.entity.Product;
import com.example.sakila.entity.Order;
import com.example.sakila.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats/revenue-by-customer")
    public List<Map<String, Object>> getRevenueByCustomer() {
        return dashboardService.getRevenueByCustomer();
    }

    @GetMapping("/stats/revenue-by-product")
    public List<Map<String, Object>> getRevenueByProduct() {
        return dashboardService.getRevenueByProduct();
    }

    @GetMapping("/stats/revenue-by-month")
    public List<Map<String, Object>> getRevenueByMonth() {
        return dashboardService.getRevenueByMonth();
    }

    @GetMapping("/search/customers")
    public List<Customer> searchCustomers(@RequestParam String query) {
        return dashboardService.searchCustomers(query);
    }

    @GetMapping("/search/products")
    public List<Product> searchProducts(@RequestParam String query) {
        return dashboardService.searchProducts(query);
    }

    @GetMapping("/search/orders")
    public List<Order> searchOrders(@RequestParam String start, @RequestParam String end) {
        return dashboardService.searchOrders(start, end);
    }
}
