package com.api.vet.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author Rodrigo Caro
 */
@Data
public class SaleDTO {

    private Integer quantity;
    
    private Double total;

    private String productId;
    
    private String clientId;

}
