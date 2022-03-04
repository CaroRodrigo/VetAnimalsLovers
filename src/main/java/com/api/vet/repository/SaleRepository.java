package com.api.vet.repository;

import com.api.vet.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rodrigo Caro
 */
public interface SaleRepository extends JpaRepository<Sale, String> {
    
}
