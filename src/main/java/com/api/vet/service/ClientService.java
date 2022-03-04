/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.vet.service;

import com.api.vet.dto.ClientDTO;
import com.api.vet.entity.Client;
import com.api.vet.exception.ClientException;
import java.util.List;

/**
 *
 * @author Rodrigo Caro
 */
public interface ClientService {
    ClientDTO save(ClientDTO dto) throws ClientException;

    Client getById(String id);

    boolean existsById(String id);

    List<ClientDTO> getAllByPage(Integer page);

    void delete(String id);

    ClientDTO update(ClientDTO dto, String id) throws ClientException;

    void validateClientsForUpdateOrCreate(ClientDTO newDTO) throws ClientException;
}
