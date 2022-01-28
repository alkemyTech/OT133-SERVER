package com.alkemy.ong.service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.alkemy.ong.entity.Rol;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.enums.Roles;
import com.alkemy.ong.repository.RolRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

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

  private boolean emailExists(String email) {
    return userRepository.findByEmail(email).isPresent();
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



  @Transactional(readOnly = true)
  public Optional<User> getByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  @Transactional(readOnly = true)
  public Optional<User> getById(UUID id) {
    return this.userRepository.findById(id.toString());
  }

  public User save(User user) {
    return this.userRepository.save(user);
  }


  public Optional<User> update(Map<Object, Object> fields, UUID id) {
    Optional<User> userOptional = this.getById(id);

    if (!userOptional.isPresent()) {
      return Optional.empty();
    } else {
      fields.forEach((key, value) -> {
        Field field = ReflectionUtils.findField(User.class, (String) key);
        field.setAccessible(true);
        ReflectionUtils.setField(field, userOptional.get(), value);
      });
      return Optional.of(this.save(userOptional.get()));
    }

  }
}
