package com.daily.shop_system.controller;

import com.daily.shop_system.model.Product;
import com.daily.shop_system.response.ApiResponse;
import com.daily.shop_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getProductById(Long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("success", product));
    }
}
