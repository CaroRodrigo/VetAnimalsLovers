/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.vet.repository;

import com.api.vet.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Rodrigo Caro
 */
public interface ImageRepository extends JpaRepository<Image, String> {
    
}
