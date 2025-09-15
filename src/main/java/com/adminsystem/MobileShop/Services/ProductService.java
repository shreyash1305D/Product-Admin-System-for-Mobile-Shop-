package com.adminsystem.MobileShop.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adminsystem.MobileShop.entities.Product;
import com.adminsystem.MobileShop.repositories.ProductRepository;

@Service
public class ProductService {

	public final ProductRepository repo;
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public void save(Product product) {
        if (product.getModelName() == null || product.getModelName().isBlank()) {
            throw new IllegalArgumentException("Model name is required");
        }

        repo.save(product);
    }
    public List<Product> getAll() {
        return repo.findAll();
    }
    public List<Product> lowStock() {
        return repo.findByStockQuantityLessThan(5);
    }
    public Product getById(int id) {
        return repo.findById(id).orElse(null);
    }
    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Product> search(String keyword) {
        return repo.findByBrandContainingIgnoreCase(keyword);
    }

    public List<Product> filterByPrice(double min, double max) {
        return repo.findByPriceBetween(min, max);
    }
    public List<Product> filterByPriceAndBrand(Double minPrice, Double maxPrice, String brand) {
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;

        if (brand == null || brand.isEmpty()) {
            return repo.findByPriceBetween(minPrice, maxPrice);
        } else {
            return repo.findByPriceBetweenAndBrandIgnoreCase(minPrice, maxPrice, brand);
        }
    }
}
