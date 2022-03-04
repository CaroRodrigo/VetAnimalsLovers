package com.api.vet.repository;

import com.api.vet.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rodrigo Caro
 */
public interface ClientRepository extends JpaRepository<Client, String> {
    
}
