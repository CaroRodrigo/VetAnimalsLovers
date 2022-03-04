package com.api.vet.service.imp;

import com.api.vet.dto.SaleDTO;
import com.api.vet.entity.Client;
import com.api.vet.entity.Product;
import com.api.vet.entity.Sale;
import com.api.vet.exception.SaleException;
import com.api.vet.mapper.SaleMapper;
import com.api.vet.repository.ClientRepository;
import com.api.vet.repository.ProductRepository;
import com.api.vet.repository.SaleRepository;
import com.api.vet.service.SaleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rodrigo Caro
 */
@Service
public class SaleServiceImpl implements SaleService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    SaleMapper saleMapper;

    @Autowired
    SaleRepository salesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public SaleDTO save(SaleDTO dto) throws SaleException {
        validateSalesForUpdateOrCreate(dto);

        Sale sales = saleMapper.saleDTO2Entity(dto);
        Sale saleSave = salesRepository.save(sales);
        SaleDTO result = saleMapper.entity2saleDTO(saleSave);
        return result;

    }

    @Override
    public Sale getById(String id) {
        return salesRepository.getById(id);
    }

    @Override
    public boolean existsById(String id) {
        return salesRepository.existsById(id);
    }

    @Override
    public List<SaleDTO> getAllByPage(Integer page) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE);
        Page<Sale> pageSale = salesRepository.findAll(paging);
        List<Sale> sales = pageSale.getContent();
        List<SaleDTO> dtos = saleMapper.entityList2saleDTOList(sales);
        return dtos;
    }

    @Override
    public void delete(String id) {
        salesRepository.deleteById(id);
    }

    @Override
    public SaleDTO update(SaleDTO dto, String id) throws SaleException {

        Optional<Sale> sales = salesRepository.findById(id);

        if (sales.isPresent()) {

            validateSalesForUpdateOrCreate(dto);

            sales.get().setQuantity(dto.getQuantity());
            sales.get().setTotal(dto.getTotal());

            SaleDTO saleDTO = saleMapper.entity2saleDTO(sales.get());

            salesRepository.save(sales.get());

            return saleDTO;
        } else {
            throw new SaleException("The Sale id does not exist in the database or is incorrect.");
        }

    }

    @Override
    public void validateSalesForUpdateOrCreate(SaleDTO saleDTO) throws SaleException {

        if (saleDTO.getClientId() == null || saleDTO.getClientId().isEmpty() || saleDTO.getClientId().equals(" ")) {
            throw new SaleException("Client null or empty.");
        }

        Optional<Client> client = clientRepository.findById(saleDTO.getClientId());

        if (saleDTO.getProductId() == null || saleDTO.getProductId().isEmpty() || saleDTO.getProductId().equals(" ")) {
            throw new SaleException("Product null or empty.");
        }
        
        Optional<Product> product = productRepository.findById(saleDTO.getProductId());

        if (saleDTO.getQuantity() == null || saleDTO.getQuantity().equals(" ")) {
            throw new SaleException("Quality null or empty.");
        }

        if (saleDTO.getTotal() == null || saleDTO.getTotal().equals(" ")) {
            throw new SaleException("Image null or empty.");
        }
    }

}
