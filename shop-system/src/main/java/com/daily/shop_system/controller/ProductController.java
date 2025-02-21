package com.daily.shop_system.controller;

import com.daily.shop_system.dto.ProductDTO;
import com.daily.shop_system.mapper.ProductMapper;
import com.daily.shop_system.model.Product;
import com.daily.shop_system.response.ApiResponse;
import com.daily.shop_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getAllProducts());
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id){
        ProductDTO product = productMapper.productToProductDTO(productService.getProductById(id));
        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO product){
        try {
            productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success", product));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Product already exists", e.getMessage()));
        }
    }

    @PostMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO product, @PathVariable("productId") Long id){
        Product check = productService.updateProduct(product, id);
        if(check == null){
            return ResponseEntity.ok(new ApiResponse("Update product failed", product));
        }
        return ResponseEntity.ok(new ApiResponse("Update product success", product));
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse("Delete product success", id));
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getProductByBrandAndName(brand, name));
        if(products.isEmpty()){
            return ResponseEntity.ok(new ApiResponse("Product not found", products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/products/by/brand-and-category")
    public ResponseEntity<ApiResponse> getProductByBrandAndCategory(@RequestParam String brand, @RequestParam String category){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getProductByCategoryAndBrand(brand, category));
        if(products.isEmpty()){
            return ResponseEntity.ok(new ApiResponse("Product not found", products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getProductByName(name));
        if(products.isEmpty()){
            return ResponseEntity.ok(new ApiResponse("Product not found", products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getProductByBrand(brand));
        if(products.isEmpty()){
            return ResponseEntity.ok(new ApiResponse("Product not found", products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/products/{category}/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        List<ProductDTO> products = productMapper.productListToProductDTOList(productService.getProductsByCategory(category));
        if(products.isEmpty()){
            return ResponseEntity.ok(new ApiResponse("Product not found", products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
        return ResponseEntity.ok(new ApiResponse("success", productService.countProductsByBrandAndName(brand, name)));
    }

}
