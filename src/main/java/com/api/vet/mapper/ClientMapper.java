package com.api.vet.mapper;

import com.api.vet.dto.ClientDTO;
import com.api.vet.entity.Client;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rodrigo Caro
 */
@Component
public class ClientMapper {
    public Client clientDTO2Entity(ClientDTO dto) {
    Client client = new Client();
    client.setFirstName(dto.getLastName());
    client.setLastName(dto.getLastName());
    client.setPhone(dto.getPhone());
    client.setEmail(dto.getEmail());
    client.setAddress(dto.getAddress());
 
    return client;
  }

  public ClientDTO entity2clientDTO(Client client) {
    ClientDTO dto = new ClientDTO();
    dto.setFirstName(client.getFirstName());
    dto.setLastName(client.getLastName());
    dto.setEmail(client.getEmail());
    dto.setPhone(client.getPhone());
    dto.setAddress(client.getAddress());
    
    return dto;
  }
  
  public List<ClientDTO> entityList2clientDTOList (List<Client> clientList){
    List<ClientDTO> dtos = new ArrayList<>();
    for (Client client: clientList ) {
      dtos.add(this.entity2clientDTO(client));
    }
    return dtos;
  }
}
