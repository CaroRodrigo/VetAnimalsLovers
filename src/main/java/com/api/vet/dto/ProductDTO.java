package com.api.vet.dto;

import com.api.vet.entity.Image;
import lombok.Data;

/**
 *
 * @author Rodrigo Caro
 */
@Data
public class ProductDTO {

    private String name;

    private String description;

    private Double purchasePrice;

    private Double salePrice;

    private Image image;

    private Integer stock;

    private String categoryId;
}
