package br.com.developers.login.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.developers.config.jwt.JwtProvider;
import br.com.developers.domain.model.Role;
import br.com.developers.domain.model.RoleName;
import br.com.developers.domain.model.User;
import br.com.developers.domain.repository.RoleRepository;
import br.com.developers.domain.repository.UserRepository;
import br.com.developers.exception.ExistingEmailException;
import br.com.developers.exception.RoleNotFoundException;
import br.com.developers.exception.UserNotFoundException;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.dto.UserDTO;
import br.com.developers.login.http.request.AccessToken;
import br.com.developers.login.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserServiceImpl implements UserService {

  private static final String ROLE_NOT_FOUND = "Fail! -> Cause: %s Role not found in database.";
  private static final String USER_NOT_FOUND = "Fail! -> Cause: User not found with email [%s]";

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder encoder;
  private JwtProvider jwtProvider;
  private AuthenticationManager authenticationManager;

  public User registerUser(RegisterDTO registerData) {
    if (this.userRepository.existsByEmail(registerData.getEmail().toLowerCase())) {
      throw new ExistingEmailException();
    }

    User user = User.newBuilder().firstName(registerData.getName())
        .lastName(registerData.getLastName()).email(registerData.getEmail().toLowerCase())
        .password(this.encoder.encode(registerData.getPassword())).build();
    Set<Role> roles = getAllRoles(registerData);

    user.setRoles(roles);
    return this.userRepository.saveAndFlush(user);

  }


  private Set<Role> getAllRoles(RegisterDTO registerData) {
    Set<String> strRoles = registerData.getRole();
    Set<Role> roles = new HashSet<>();
    strRoles.forEach(role -> {
      RoleName roleName = RoleName.find(role);
      Role permission = this.roleRepository.findByName(roleName)
          .orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND, role)));
      roles.add(permission);
    });
    return roles;
  }


  public User updateUser(RegisterDTO registerData) {
    User user = this.userRepository.findByEmail(registerData.getEmail().toLowerCase()).orElseThrow(
        () -> new UserNotFoundException(String.format(USER_NOT_FOUND, registerData.getEmail())));
    user.setFirstName(registerData.getName());
    user.setLastName(registerData.getLastName());
    user.setPassword(registerData.getPassword());
    user.setRoles(getAllRoles(registerData));
    return this.userRepository.save(user);
  }

  public void delete(LoginDTO loginDTO) {
    User user = this.userRepository.findByEmail(loginDTO.getEmail().toLowerCase()).orElseThrow(
        () -> new UserNotFoundException(String.format(USER_NOT_FOUND, loginDTO.getEmail())));
    this.userRepository.delete(user);
  }

  public AccessToken authenticateUser(LoginDTO loginDto) {
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new AccessToken(jwtProvider.generateJwtToken(authentication));
  }

  public UserDTO find(String id) {
    User user = this.userRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
    return UserDTO.build(user);
  }
}
