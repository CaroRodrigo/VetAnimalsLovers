package com.api.vet.mapper;

import com.api.vet.dto.CategoryDTO;
import com.api.vet.entity.Category;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rodrigo Caro
 */
@Component
public class CategoryMapper {

    public Category categoryDTO2Entity(CategoryDTO dto, Category category) {
        category.setName(dto.getName());
        return category;
    }

    public Category categoryDTO2Entity2(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public CategoryDTO categoryEntity2DTO(Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(entity.getName());
        return categoryDTO;

    }

    public LocalDateTime string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(stringDate, formatter);
    }

    public String LocalDate2String(LocalDateTime localDateTime) {
        DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_DATE;
        return localDateTime.format(isoFecha);
    }
}
