package com.api.vet.controller;

import com.api.vet.dto.ProductDTO;
import com.api.vet.entity.Product;
import com.api.vet.exception.ProductException;
import com.api.vet.exception.WebException;
import com.api.vet.mapper.ProductMapper;
import com.api.vet.service.CategoryService;
import com.api.vet.service.ProductService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;
    
        @Autowired
    CategoryService categoryService;

    @Autowired
    ProductMapper productMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public String saveProduct(Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) MultipartFile imagen, Product product) throws IOException {
        try {
            productService.save2(product, imagen);
            //redirectAttributes.addFlashAttribute("error", "Primer paso completado exitosamente");  
        } catch (WebException ex) {
            ex.printStackTrace();
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.listAll());
            return "movies-form";
        }
        return "redirect:/movies/list";
    }

    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Map<String, Object> response = new HashMap<>();

        if (!this.productService.existsById(id.toString())) {
            response.put("NotFound", String.format("Product with ID %s not found", id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("ok", this.productMapper.entity2productDTO(this.productService.getById(id.toString())));
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Object>> getAllByPage(@PathVariable int page) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (page > 0) {
                response.put("url previus", String.format("localhost:8080/products/page/%d", page - 1));
            }
            if (!productService.getAllByPage(page + 1).isEmpty()) {
                response.put("url next", String.format("localhost:8080/products/page/%d", page + 1));
            }
            response.put("ok", productService.getAllByPage(page));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ProductDTO newDto) {

        try {
            ProductDTO newDTOS = productService.update(newDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(newDTOS);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!productService.existsById(id)) {
            return new ResponseEntity("The Product id does not exist in the database or is incorrect.", HttpStatus.NOT_FOUND);
        }
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
