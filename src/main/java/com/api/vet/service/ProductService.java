/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.vet.service;

import com.api.vet.dto.ProductDTO;
import com.api.vet.entity.Product;
import com.api.vet.exception.ProductException;
import com.api.vet.exception.WebException;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rodrigo Caro
 */
public interface ProductService {

    ProductDTO save(ProductDTO dto) throws ProductException;
    
    Product save2(Product product, MultipartFile image) throws WebException, IOException;

    Product getById(String id);

    boolean existsById(String id);

    List<ProductDTO> getAllByPage(Integer page);

    void delete(String id);

    ProductDTO update(ProductDTO dto, String id) throws ProductException;

    void validateProductsForUpdateOrCreate(ProductDTO newDTO) throws ProductException;
}
