package com.api.vet.controller;

import com.api.vet.dto.ClientDTO;
import com.api.vet.exception.ClientException;
import com.api.vet.mapper.ClientMapper;
import com.api.vet.service.ClientService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    ClientMapper clientMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> save(@RequestBody ClientDTO dto) {

        try {

            ClientDTO clientDTOS = clientService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientDTOS);

        } catch (ClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Map<String, Object> response = new HashMap<>();

        if (!this.clientService.existsById(id.toString())) {
            response.put("NotFound", String.format("Client with ID %s not found", id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("ok", this.clientMapper.entity2clientDTO(this.clientService.getById(id.toString())));
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Object>> getAllByPage(@PathVariable int page) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (page > 0) {
                response.put("url previus", String.format("localhost:8080/clients/page/%d", page - 1));
            }
            if (!clientService.getAllByPage(page + 1).isEmpty()) {
                response.put("url next", String.format("localhost:8080/clients/page/%d", page + 1));
            }
            response.put("ok", clientService.getAllByPage(page));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ClientDTO newDto) {

        try {
            ClientDTO newDTOS = clientService.update(newDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(newDTOS);
        } catch (ClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!clientService.existsById(id)) {
            return new ResponseEntity("The Client id does not exist in the database or is incorrect.", HttpStatus.NOT_FOUND);
        }
        clientService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
