package com.api.vet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Rodrigo Caro
 */
@Getter
@Setter
@ToString
public class CategoryDTO {

    @NotBlank(message = "no es un valor valido.")
    @Size(message = "debe tene minimo 4 caracteres", min = 4)
    private String name;

}
