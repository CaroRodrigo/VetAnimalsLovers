package com.api.vet.dto;

import com.api.vet.entity.Rol;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Rodrigo Caro
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTOAll {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  //private String photo;
  private String password;
  private List<Rol> roles;


}
