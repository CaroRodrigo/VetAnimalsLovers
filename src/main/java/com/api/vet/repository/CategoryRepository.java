package com.api.vet.repository;

import com.api.vet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rodrigo Caro
 */
public interface CategoryRepository extends JpaRepository<Category, String> {
    
}
