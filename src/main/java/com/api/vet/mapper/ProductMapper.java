package com.api.vet.mapper;

import com.api.vet.dto.ProductDTO;
import com.api.vet.entity.Image;
import com.api.vet.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rodrigo Caro
 */
@Component
public class ProductMapper {
    public Product productDTO2Entity(ProductDTO dto) {
    Product product = new Product();
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setImage(dto.getImage());
    product.setPurchasePrice(dto.getPurchasePrice());
    product.setSalePrice(dto.getSalePrice());
    product.setStock(dto.getStock());
    product.setCategoryId(dto.getCategoryId());
    return product;
  }

  public ProductDTO entity2productDTO(Product product) {
    ProductDTO dto = new ProductDTO();
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setImage(product.getImage());
    dto.setPurchasePrice(product.getPurchasePrice());
    dto.setSalePrice(product.getSalePrice());
    dto.setStock(product.getStock());

    
    return dto;
  }
  
  public List<ProductDTO> entityList2productDTOList (List<Product> productList){
    List<ProductDTO> dtos = new ArrayList<>();
    for (Product product: productList ) {
      dtos.add(this.entity2productDTO(product));
    }
    return dtos;
  }

    public Product productDTO2Entity(ProductDTO dto, MultipartFile archive) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
