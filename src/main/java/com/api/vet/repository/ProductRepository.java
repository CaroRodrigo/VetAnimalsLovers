package com.api.vet.repository;

import com.api.vet.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rodrigo Caro
 */
public interface ProductRepository extends JpaRepository<Product, String>{
    
}
