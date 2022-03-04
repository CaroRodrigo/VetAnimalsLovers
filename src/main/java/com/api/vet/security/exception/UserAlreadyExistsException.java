package com.api.vet.security.exception;

/**
 *
 * @author Rodrigo Caro
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

