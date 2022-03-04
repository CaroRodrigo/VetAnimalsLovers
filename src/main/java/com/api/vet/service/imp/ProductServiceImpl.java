package com.api.vet.service.imp;

import com.api.vet.dto.ProductDTO;
import com.api.vet.entity.Category;
import com.api.vet.entity.Image;
import com.api.vet.entity.Product;
import com.api.vet.exception.ProductException;
import com.api.vet.exception.WebException;
import com.api.vet.mapper.ProductMapper;
import com.api.vet.repository.CategoryRepository;
import com.api.vet.repository.ProductRepository;
import com.api.vet.service.ImageService;
import com.api.vet.service.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rodrigo Caro
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public ProductDTO save(ProductDTO dto) throws ProductException {
        validateProductsForUpdateOrCreate(dto);
        Product products = productMapper.productDTO2Entity(dto);
        Product productSave = productRepository.save(products);
        ProductDTO result = productMapper.entity2productDTO(productSave);
        return result;

    }

    @Override
    public Product save2(Product product, MultipartFile image) throws WebException, IOException {
        if (product.getCategoryId() == null || product.getCategoryId().isEmpty() || product.getCategoryId().equals(" ")) {
            throw new WebException("Category null or empty.");
        }

        Optional<Category> category = categoryRepository.findById(product.getCategoryId());

        if (product.getName() == null || product.getName().isEmpty() || product.getName().equals(" ")) {
            throw new WebException("Name null or empty.");
        }
        if (product.getDescription() == null || product.getDescription().isEmpty() || product.getDescription().equals(" ")) {
            throw new WebException("Description null or empty.");
        }
        if (product.getImage() == null) {
            throw new WebException("Image null or empty.");
        }
        if (product.getSalePrice() == null || product.getSalePrice().equals(" ")) {
            throw new WebException("Image null or empty.");
        }
        if (product.getStock() == null || product.getStock().equals(" ")) {
            throw new WebException("Image null or empty.");
        }
        if (!category.isPresent()) {
            throw new WebException("Category does not exist in the database.");
        }
        Image img = imageService.saveImage(image);
        product.setImage(img);
        return productRepository.save(product);

    }

    @Override
    public Product getById(String id) {
        return productRepository.getById(id);
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }

    @Override
    public List<ProductDTO> getAllByPage(Integer page) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE);
        Page<Product> pageProduct = productRepository.findAll(paging);
        List<Product> products = pageProduct.getContent();
        List<ProductDTO> dtos = productMapper.entityList2productDTOList(products);
        return dtos;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO update(ProductDTO dto, String id) throws ProductException {

        Optional<Product> products = productRepository.findById(id);

        if (products.isPresent()) {

            validateProductsForUpdateOrCreate(dto);

            products.get().setName(dto.getName());
            products.get().setImage(dto.getImage());
            products.get().setDescription(dto.getDescription());
            products.get().setPurchasePrice(dto.getPurchasePrice());
            products.get().setSalePrice(dto.getSalePrice());
            products.get().setStock(dto.getStock());

            ProductDTO productDTO = productMapper.entity2productDTO(products.get());

            productRepository.save(products.get());

            return productDTO;
        } else {
            throw new ProductException("The Product id does not exist in the database or is incorrect.");
        }

    }

    @Override
    public void validateProductsForUpdateOrCreate(ProductDTO productDTO) throws ProductException {

        if (productDTO.getCategoryId() == null || productDTO.getCategoryId().isEmpty() || productDTO.getCategoryId().equals(" ")) {
            throw new ProductException("Category null or empty.");
        }

        Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());

        if (productDTO.getName() == null || productDTO.getName().isEmpty() || productDTO.getName().equals(" ")) {
            throw new ProductException("Name null or empty.");
        }
        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty() || productDTO.getDescription().equals(" ")) {
            throw new ProductException("Description null or empty.");
        }
        if (productDTO.getImage() == null) {
            throw new ProductException("Image null or empty.");
        }
        if (productDTO.getSalePrice() == null || productDTO.getSalePrice().equals(" ")) {
            throw new ProductException("Image null or empty.");
        }
        if (productDTO.getStock() == null || productDTO.getStock().equals(" ")) {
            throw new ProductException("Image null or empty.");
        }
        if (!category.isPresent()) {
            throw new ProductException("Category does not exist in the database.");
        }
    }

}
