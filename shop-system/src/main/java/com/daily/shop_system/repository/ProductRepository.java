package com.daily.shop_system.repository;

import com.daily.shop_system.model.Category;
import com.daily.shop_system.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Period;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByName(String productName);
    List<Product> findByBrandAndName(String brand, String productName);
    Long countByBrandAndName(String brand, String productName);

}
