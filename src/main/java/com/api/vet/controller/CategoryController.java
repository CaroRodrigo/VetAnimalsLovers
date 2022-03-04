package com.api.vet.controller;

import com.api.vet.dto.CategoryDTO;
import com.api.vet.service.CategoryService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO category) throws Exception {

        try {
            CategoryDTO categorySaved = categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(categorySaved);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
    
     @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable String id) {
        CategoryDTO categoryDetails = categoryService.findCategoryById(id);
        if (categoryDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryDetails);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@Validated @RequestBody CategoryDTO categoryDTO,
            @PathVariable UUID id) {

        Map<String, Object> response = new HashMap<>();

        Optional<CategoryDTO> optCategoryDTO = this.categoryService.updateCategory(categoryDTO, id);

        if (!optCategoryDTO.isPresent()) {
            response.put("Error", String.format("Category with ID %s not found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("ok", optCategoryDTO);
            return ResponseEntity.ok(response);
        }
    }
}
