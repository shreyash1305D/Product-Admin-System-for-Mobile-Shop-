package com.adminsystem.MobileShop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminsystem.MobileShop.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByBrandContainingIgnoreCase(String brand);
    List<Product> findByModelNameContainingIgnoreCase(String modelName);
    List<Product> findByPriceBetween(double min, double max);
    List<Product> findByStockQuantityLessThan(int threshold);
    List<Product> findByPriceBetweenAndBrandIgnoreCase(Double min, Double max, String brand);

}
