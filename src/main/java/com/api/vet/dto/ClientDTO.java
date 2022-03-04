package com.api.vet.dto;

import lombok.Data;

/**
 *
 * @author Rodrigo Caro
 */
@Data
public class ClientDTO {
    
    private String firstName;

    private String lastName;

    private String email;
    
    private String phone;

    private String address;
    
}
