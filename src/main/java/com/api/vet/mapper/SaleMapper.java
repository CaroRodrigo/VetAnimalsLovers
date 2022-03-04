package com.api.vet.mapper;

import com.api.vet.dto.SaleDTO;
import com.api.vet.entity.Sale;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rodrigo Caro
 */
@Component
public class SaleMapper {
    public Sale saleDTO2Entity(SaleDTO dto) {
    Sale sale = new Sale();
    sale.setQuantity(dto.getQuantity());
    sale.setTotal(dto.getTotal());
    sale.setProductId(dto.getProductId());
    sale.setClientId(dto.getClientId());

    return sale;
  }

  public SaleDTO entity2saleDTO(Sale sale) {
    SaleDTO dto = new SaleDTO();
    dto.setQuantity(sale.getQuantity());
    dto.setTotal(sale.getTotal());
    dto.setProductId(sale.getClientId());
    dto.setClientId(sale.getClientId());
    
    return dto;
  }
  
  public List<SaleDTO> entityList2saleDTOList (List<Sale> saleList){
    List<SaleDTO> dtos = new ArrayList<>();
    for (Sale sale: saleList ) {
      dtos.add(this.entity2saleDTO(sale));
    }
    return dtos;
  }
}
