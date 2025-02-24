package com.daily.shop_system.service;

import com.daily.shop_system.dto.ProductDTO;
import com.daily.shop_system.model.Category;
import com.daily.shop_system.model.Product;
import com.daily.shop_system.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product addProduct(ProductDTO product) throws Exception {

        if(productExists(product.getName(), product.getBrand())) {
            throw new Exception(product.getBrand() + " " + product.getName() + " already exists ");
        }

        Category category = Optional.ofNullable(categoryService.getCategoryByName(product.getCategory().getName())).orElseGet(()-> {
            Category newCategory = new Category();
            newCategory.setName(product.getCategory().getName());
            return categoryService.addCategory(newCategory);
        });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(ProductDTO product, Category category) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setBrand(product.getBrand());
        newProduct.setPrice(product.getPrice());
        newProduct.setInventory(product.getInventory());
        newProduct.setDescription(product.getDescription());
        newProduct.setCategory(category);
        return newProduct;

    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(ProductDTO updatedProduct, Long id){

        Product product = productRepository.findById(id).orElse(null);
        if(product==null){
            return null;
        }
        product.setName(updatedProduct.getName());
        product.setBrand(updatedProduct.getBrand());
        product.setPrice(updatedProduct.getPrice());
        product.setInventory(updatedProduct.getInventory());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategoryName(category);
    }

    public List<Product> getProductByBrand(String brand){
        return productRepository.findByBrand(brand);
    }

    public List<Product> getProductByCategoryAndBrand(String category, String brand){
        Category findCategory = categoryService.getCategoryByName(category);
        return productRepository.findByCategoryIdAndBrand(findCategory.getId(), brand);
    }

    public List<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> getProductByBrandAndName(String brand, String name){
        return productRepository.findByBrandAndName(brand, name);
    }

    public Long countProductsByBrandAndName(String brand, String name){
        return productRepository.countByBrandAndName(brand, name);
    }



}
