package com.api.vet.service;

import com.api.vet.dto.UserDTO;
import com.api.vet.dto.UserDTOAll;
import com.api.vet.entity.User;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Rodrigo Caro
 */
public interface UserService {
	
	Iterable<User> listAll();
	
	UserDTO save(UserDTO dto);

	void deleteUser(String id); 

	boolean userExists(String id);
	
	Optional<User> update(Map<Object, Object> fields, UUID id);
	
	Optional<User> getByEmail(String email);

  UserDTOAll getUserDetails(String authorizationHeader);
}
