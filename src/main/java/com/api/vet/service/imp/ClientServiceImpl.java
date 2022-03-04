/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.vet.service.imp;

import com.api.vet.dto.ClientDTO;
import com.api.vet.entity.Category;
import com.api.vet.entity.Client;
import com.api.vet.exception.ClientException;
import com.api.vet.mapper.ClientMapper;
import com.api.vet.repository.CategoryRepository;
import com.api.vet.repository.ClientRepository;
import com.api.vet.service.ClientService;
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
public class ClientServiceImpl implements ClientService{
    private static final int PAGE_SIZE = 10;

  @Autowired
  ClientMapper clientMapper;

  @Autowired
  ClientRepository clientsRepository;

  @Autowired
  private CategoryRepository categoryRepository;
  
  @Override
  public ClientDTO save(ClientDTO dto)throws ClientException {
    validateClientsForUpdateOrCreate(dto);
    
    Client clients = clientMapper.clientDTO2Entity(dto);
    Client clientSave = clientsRepository.save(clients);
    ClientDTO result = clientMapper.entity2clientDTO(clientSave);
    return result;

  }

  @Override
  public Client getById(String id) {
    return clientsRepository.getById(id);
  }

  @Override
  public boolean existsById(String id) {
    return clientsRepository.existsById(id);
  }

  @Override
  public List<ClientDTO> getAllByPage(Integer page) {
    Pageable paging = PageRequest.of(page, PAGE_SIZE);
    Page<Client> pageClient = clientsRepository.findAll(paging);
    List<Client> clients = pageClient.getContent();
    List<ClientDTO> dtos = clientMapper.entityList2clientDTOList(clients);
    return dtos;
  }


  @Override
  public void delete(String id) {
    clientsRepository.deleteById(id);
  }


  @Override
  public ClientDTO update(ClientDTO dto, String id) throws ClientException {
    
    Optional<Client> clients = clientsRepository.findById(id);

    if (clients.isPresent()) {
      
      validateClientsForUpdateOrCreate(dto);
      
      clients.get().setFirstName(dto.getFirstName());
      clients.get().setLastName(dto.getLastName());
      clients.get().setEmail(dto.getEmail());
      clients.get().setPhone(dto.getPhone());
      clients.get().setAddress(dto.getAddress());

      ClientDTO clientDTO = clientMapper.entity2clientDTO(clients.get());

      clientsRepository.save(clients.get());

      return clientDTO;
    } else {
      throw new ClientException("The Client id does not exist in the database or is incorrect.");
    }

  }

  @Override
  public void validateClientsForUpdateOrCreate(ClientDTO clientDTO) throws ClientException {
    
    if (clientDTO.getFirstName() == null ||clientDTO.getFirstName().isEmpty() ||clientDTO.getFirstName().equals(" ")) {
      throw new ClientException("Name null or empty.");
    }
    if (clientDTO.getLastName()== null||clientDTO.getLastName().isEmpty() ||clientDTO.getLastName().equals(" ")) {
      throw new ClientException("Description null or empty.");
    }
    if (clientDTO.getEmail() == null||clientDTO.getEmail().isEmpty() ||clientDTO.getEmail().equals(" ")) {
      throw new ClientException("Image null or empty.");
    }
        if (clientDTO.getPhone() == null ||clientDTO.getPhone().equals(" ")) {
      throw new ClientException("Image null or empty.");
    }

  }

}
