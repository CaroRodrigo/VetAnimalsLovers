package com.api.vet.controller;

import com.api.vet.dto.SaleDTO;
import com.api.vet.exception.SaleException;
import com.api.vet.mapper.SaleMapper;
import com.api.vet.service.SaleService;
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
@RequestMapping("/sales")
public class SaleController {
        @Autowired
    SaleService saleService;

    @Autowired
    SaleMapper saleMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> save(@RequestBody SaleDTO dto) {

        try {

            SaleDTO saleDTOS = saleService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saleDTOS);

        } catch (SaleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Map<String, Object> response = new HashMap<>();

        if (!this.saleService.existsById(id.toString())) {
            response.put("NotFound", String.format("Sale with ID %s not found", id.toString()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put("ok", this.saleMapper.entity2saleDTO(this.saleService.getById(id.toString())));
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Map<String, Object>> getAllByPage(@PathVariable int page) {
        try {
            Map<String, Object> response = new HashMap<>();
            if (page > 0) {
                response.put("url previus", String.format("localhost:8080/sales/page/%d", page - 1));
            }
            if (!saleService.getAllByPage(page + 1).isEmpty()) {
                response.put("url next", String.format("localhost:8080/sales/page/%d", page + 1));
            }
            response.put("ok", saleService.getAllByPage(page));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody SaleDTO newDto) {

        try {
            SaleDTO newDTOS = saleService.update(newDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(newDTOS);
        } catch (SaleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!saleService.existsById(id)) {
            return new ResponseEntity("The Sale id does not exist in the database or is incorrect.", HttpStatus.NOT_FOUND);
        }
        saleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
