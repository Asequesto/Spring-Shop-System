package com.daily.shop_system.controller;

import com.daily.shop_system.model.Category;
import com.daily.shop_system.response.ApiResponse;
import com.daily.shop_system.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204 No Content
                    .body(new ApiResponse("No categories found", null));
        }

        return ResponseEntity.ok(new ApiResponse("Success", categories));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok(new ApiResponse("Success", category));
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204 No Content
                    .body(new ApiResponse("No categories found", null));
        }
        return ResponseEntity.ok(new ApiResponse("Success", category));
    }

    @GetMapping("/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        Category category = categoryService.getCategoryByName(name);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204 No Content
                    .body(new ApiResponse("No categories found", null));
        }
        return ResponseEntity.ok(new ApiResponse("Success", category));
    }

    @DeleteMapping ("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse("Success", null));
    }

    @PutMapping ("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Category updateCategory = categoryService.updateById(category, id);
        if(updateCategory.getId() == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204 No Content
                    .body(new ApiResponse("No categories found", null));
        }
        return ResponseEntity.ok(new ApiResponse("Success", null));
    }

}
