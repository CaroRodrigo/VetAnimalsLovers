package com.api.vet.service.imp;

import com.api.vet.dto.CategoryDTO;
import com.api.vet.entity.Category;
import com.api.vet.exception.CategoryException;
import com.api.vet.mapper.CategoryMapper;
import com.api.vet.repository.CategoryRepository;
import com.api.vet.service.CategoryService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rodrigo Caro
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryDTO create(CategoryDTO categoryDTO) throws Exception {
        verifyCategory(categoryDTO);
        Category category = categoryMapper.categoryDTO2Entity2(categoryDTO);
        Category categorySaved = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.categoryEntity2DTO(categorySaved);
        return result;
    }

    public void verifyCategory(CategoryDTO category) throws CategoryException {

        if (category.getName() == null || category.getName().isEmpty()) {
            throw new CategoryException("Name null or empty");
        }
    }

    @Override
    public CategoryDTO findCategoryById(String id) {
        try {
            Category foundCategory = categoryRepository.findById(id).get();
            return categoryMapper.categoryEntity2DTO(foundCategory);
        } catch (Exception e) {
            System.out.println("No encontrado");
        }
        return null;

    }

    @Override
    public CategoryDTO findById(String id) {
        Category category = categoryRepository.findById(id).get();
        return categoryMapper.categoryEntity2DTO(category);
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> listAll() {
        List<Category> list = categoryRepository.findAll();
        return list;
    }

    @Override
    public Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id) {
        Optional<Category> optCategory = this.categoryRepository.findById(id.toString());

        if (!optCategory.isPresent()) {
            return Optional.empty();
        } else {
            Category categoryUpdate
                    = this.categoryMapper.categoryDTO2Entity(categoryDTO, optCategory.get());

            return Optional
                    .of(this.categoryMapper.categoryEntity2DTO(this.categoryRepository.save(categoryUpdate)));
        }
    }
}
