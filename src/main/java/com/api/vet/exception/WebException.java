/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.vet.exception;

/**
 *
 * @author Rodrigo Caro
 */
public class WebException extends Exception {
    public WebException(String message) {
        super(message);
    }
}
