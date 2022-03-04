package com.api.vet.controller;

import com.api.vet.entity.Product;
import com.api.vet.exception.ImageWebException;
import com.api.vet.service.ProductService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Rodrigo Caro
 */
 @Controller
    @RequestMapping("/images")
public class ImageController {

        @Autowired
        public ProductService productService;

        @GetMapping("/products")
        public ResponseEntity<byte[]> imagenPelicula(@RequestParam String id) throws ImageWebException {
            try {
                Product product = productService.getById(id);
                byte[] image = product.getImage().getContents();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } catch (Exception e) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
