package com.api.vet.controller;

import com.api.vet.entity.User;
import com.api.vet.security.UserDetailServiceImpl;
import com.api.vet.security.exception.UserAlreadyExistsException;
import com.api.vet.security.payload.SignupRequest;
import com.api.vet.service.UserDAO;
import com.api.vet.service.UserService;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetails;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(path = "register", produces = "application/json")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest)
            throws UserAlreadyExistsException, IOException {
        // Creacion de nuevo usuario
        User user = new User();
        user.setFirstName(signupRequest.getFirstname());
        user.setLastName(signupRequest.getLastname());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());

        // Creacion de la URI
        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());

        return ResponseEntity.created(uri).body(userDAO.create(user));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserExistExceptions() {
        Map<String, String> errors = new HashMap<>();
        errors.put("mail", "The indicated email address is already in use");
        return errors;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        boolean authenticated = false;

        try {
            this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            UserDetails userDetails = this.userDetails.loadUserByUsername(email);

            if (userDetails != null) {
                authenticated = this.passwordEncoder.matches(password, userDetails.getPassword());
                if (authenticated) {
                    response.put("ok", userDetails);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.put("Forbidden", HttpStatus.FORBIDDEN);
            }

        } catch (BadCredentialsException e) {
            response.put("Forbidden", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUserDetails(
            @RequestHeader(value = "Authorization") String authorizationHeader) {
        return new ResponseEntity<>(userService.getUserDetails(authorizationHeader), HttpStatus.OK);

    }

}
