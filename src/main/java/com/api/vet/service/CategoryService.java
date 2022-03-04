package com.api.vet.service;

import com.api.vet.dto.CategoryDTO;
import com.api.vet.entity.Category;
import com.api.vet.exception.CategoryException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Rodrigo Caro
 */
public interface CategoryService {

    CategoryDTO create(CategoryDTO categoryDTO) throws Exception;

    public void verifyCategory(CategoryDTO category) throws CategoryException;
    
    public List<Category> listAll();

    CategoryDTO findCategoryById(String id);

    CategoryDTO findById(String id);

    void delete(String id);

    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id);
}
