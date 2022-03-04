package com.api.vet.repository;

import com.api.vet.entity.Rol;
import com.api.vet.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rodrigo Caro
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, String> {

    Rol findByName(Roles rolUser);
}
