package com.api.vet.controller;

import com.api.vet.entity.User;
import com.api.vet.exception.UserException;
import com.api.vet.mapper.UserMapper;
import com.api.vet.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Rodrigo Caro
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<User>> listAll() {
    try {
      List<User> users = StreamSupport.stream(userService.listAll().spliterator(), false)
          .collect(Collectors.toList());

      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (HttpClientErrorException.BadRequest ex) {
      return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable String id) throws UserException, IOException {
    Map<String, Object> response = new HashMap<>();
    if (userService.userExists(id)) {
      userService.deleteUser(id);
      response.put("User eliminado", "Id: " + id);
      return ResponseEntity.ok(response);
    }
    response.put("Problem to ejecute process", HttpStatus.CONFLICT);
    return ResponseEntity.ok().body(response);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserException.class)
  public Map<String, String> handleUserNotFoundExceptions() {
    Map<String, String> error = new HashMap<>();
    error.put("Ok: " + Boolean.FALSE, "Could not find user by the given ID");
    return error;
  }


  @PatchMapping("/{id}")
  public ResponseEntity<?> updateUser(@RequestBody Map<Object, Object> fields,
      @PathVariable UUID id) {
    Map<String, Object> response = new HashMap<>();
    Optional<User> userOptional = this.userService.update(fields, id);

    if (!userOptional.isPresent()) {
      response.put("Error", String.format("User with ID %s not found.", id));
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } else {
      response.put("ok", this.userMapper.toUserDTO(userOptional.get()));
      return ResponseEntity.ok(response);
    }

  }
}

