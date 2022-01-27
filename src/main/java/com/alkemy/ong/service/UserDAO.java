package com.alkemy.ong.service;

import java.util.Optional;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder pwEncoder;

	// --------------------------------------------------------------------------------------------
	// Get
	// --------------------------------------------------------------------------------------------

	public Optional<User> getByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	// --------------------------------------------------------------------------------------------
	// Create
	// --------------------------------------------------------------------------------------------

	public User create(User user) throws UserAlreadyExistsException {

		if (emailExists(user.getEmail())) {
			throw new UserAlreadyExistsException(
					"The email " + user.getEmail() + " already exists already in use");
		}

		Rol rol = rolRepository.findByName(Roles.ROL_USER);

		user.setPassword(pwEncoder.encode(user.getPassword()));
		user.setRoleId(rol);

		User created = userRepository.save(user);

		return created;
	}

	// --------------------------------------------------------------------------------------------
	// Internal Methods
	// --------------------------------------------------------------------------------------------

	private boolean emailExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

}
