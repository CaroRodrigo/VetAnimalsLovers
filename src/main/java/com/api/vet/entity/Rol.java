package com.api.vet.entity;

import com.api.vet.enums.Roles;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Rodrigo Caro
 */
@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Rol extends PersistentEntity {
    @Column(name = "name", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles name;

    @Column(name = "description", nullable = false)
    private String description;
}
