package br.com.developers.login.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.developers.config.jwt.JwtProvider;
import br.com.developers.login.domain.model.Role;
import br.com.developers.login.domain.model.Role.RoleName;
import br.com.developers.login.domain.model.User;
import br.com.developers.login.domain.repository.RoleRepository;
import br.com.developers.login.domain.repository.UserRepository;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.exception.ExistingEmailException;
import br.com.developers.login.exception.IllegalRoleException;
import br.com.developers.login.http.request.AccessToken;
import br.com.developers.login.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserServiceImpl implements UserService {

  private static final String ROLE_NOT_FOUND = "Fail! -> Cause: %s Role not find.";
  private static final String ROLE_INVALID = "Fail! -> Cause: Role invalid.";

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder encoder;
  private JwtProvider jwtProvider;
  private AuthenticationManager authenticationManager;

  public User registerUser(RegisterDTO registerData) {
    if (this.userRepository.existsByEmail(registerData.getEmail())) {
      throw new ExistingEmailException();
    }

    User user = User.newBuilder().firstName(registerData.getName())
        .lastName(registerData.getLastName()).email(registerData.getEmail())
        .password(this.encoder.encode(registerData.getPassword())).build();
    Set<String> strRoles = registerData.getRole();
    Set<Role> roles = new HashSet<>();

    strRoles.forEach(role -> {
      switch (role.toLowerCase()) {
        case "admin":
          Role admin = this.roleRepository.findByName(
              RoleName.ROLE_ADMIN)
              .orElseThrow(() -> new IllegalRoleException(String.format(ROLE_NOT_FOUND, "Admin")));
          roles.add(admin);
          break;
        default:
          throw new IllegalRoleException(ROLE_INVALID);
      }
    });

    user.setRoles(roles);
    return this.userRepository.saveAndFlush(user);

  }

  public AccessToken authenticateUser(LoginDTO loginDto) {
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new AccessToken(jwtProvider.generateJwtToken(authentication));
  }
}
