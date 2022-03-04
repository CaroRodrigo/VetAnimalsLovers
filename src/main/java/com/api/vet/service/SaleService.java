package com.api.vet.service;

import com.api.vet.dto.SaleDTO;
import com.api.vet.entity.Sale;
import com.api.vet.exception.SaleException;
import java.util.List;

/**
 *
 * @author Rodrigo Caro
 */
public interface SaleService {
    SaleDTO save(SaleDTO dto) throws SaleException;

    Sale getById(String id);

    boolean existsById(String id);

    List<SaleDTO> getAllByPage(Integer page);

    void delete(String id);

    SaleDTO update(SaleDTO dto, String id) throws SaleException;

    void validateSalesForUpdateOrCreate(SaleDTO newDTO) throws SaleException;
}
