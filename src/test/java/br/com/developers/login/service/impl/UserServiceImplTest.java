package br.com.developers.login.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.BDDMockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.developers.config.jwt.JwtProvider;
import br.com.developers.constants.UserConstantsForTests;
import br.com.developers.domain.model.Role;
import br.com.developers.domain.model.RoleName;
import br.com.developers.domain.model.User;
import br.com.developers.domain.repository.RoleRepository;
import br.com.developers.domain.repository.UserRepository;
import br.com.developers.exception.ExistingEmailException;
import br.com.developers.exception.IllegalRoleException;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.provider.LoginDTOProviderTest;
import br.com.developers.provider.RegisterDTOProviderTests;

class UserServiceImplTest implements UserConstantsForTests {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder encoder;
  private JwtProvider jwtProvider;
  private AuthenticationManager authenticationManager;
  private UserServiceImpl service;

  private Role role;
  private User user;

  @BeforeEach
  void setUp() {
    HashSet<Role> roles = new HashSet<>();
    this.role = Role.newBuilder().name(RoleName.ROLE_ADMIN).id(1L).build();
    roles.add(this.role);
    this.user = User.newBuilder().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
        .roles(roles).build();

    this.userRepository = spy(UserRepository.class);
    this.roleRepository = spy(RoleRepository.class);
    this.encoder = spy(PasswordEncoder.class);
    this.jwtProvider = spy(JwtProvider.class);
    this.authenticationManager = spy(AuthenticationManager.class);
    this.service = new UserServiceImpl(this.userRepository, this.roleRepository, this.encoder,
        this.jwtProvider, this.authenticationManager);
  }

  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldRegiterUser(RegisterDTO registerData) {
    BDDMockito.given(this.roleRepository.findByName(RoleName.ROLE_ADMIN))
        .willReturn(Optional.of(this.role));
    BDDMockito.given(this.encoder.encode(registerData.getPassword())).willReturn(anyString());
    BDDMockito.given(this.userRepository.existsByEmail(EMAIL)).willReturn(Boolean.FALSE);
    BDDMockito.given(this.userRepository.saveAndFlush(this.user)).willReturn(any(User.class));

    this.service.registerUser(registerData);

    verify(this.userRepository, times(1)).existsByEmail(anyString());
    verify(this.encoder, times(1)).encode(anyString());
    verify(this.roleRepository, times(1)).findByName(any());
    verify(this.userRepository, times(1)).saveAndFlush(any(User.class));

  }

  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldReturnExistingEmailException(RegisterDTO registerData) {
    BDDMockito.given(this.userRepository.existsByEmail(EMAIL)).willReturn(Boolean.TRUE);

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.registerUser(registerData);
    });

    assertTrue(exception instanceof ExistingEmailException);
    assertEquals("Failed -> Email is already in use!", exception.getMessage());

    verify(this.userRepository, times(1)).existsByEmail(anyString());
  }

  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldReturnIllegalRoleException(RegisterDTO registerData) {

    HashSet<String> roles = new HashSet<>();
    roles.add("teste");
    registerData.setRole(roles);

    BDDMockito.given(this.roleRepository.findByName(RoleName.ROLE_ADMIN))
        .willReturn(Optional.empty());

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.registerUser(registerData);
    });

    assertTrue(exception instanceof IllegalRoleException);
    assertEquals("Fail! -> Cause: teste is Role invalid.", exception.getMessage());
  }

  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldUpdateUser(RegisterDTO registerData) {
    BDDMockito.given(this.userRepository.findByEmail(registerData.getEmail().toLowerCase()))
        .willReturn(Optional.of(this.user));
    BDDMockito.given(this.roleRepository.findByName(RoleName.ROLE_ADMIN))
        .willReturn(Optional.of(this.role));
    BDDMockito.given(this.userRepository.save(this.user)).willReturn(this.user);

    User user = this.service.updateUser(registerData);

    assertEquals(registerData.getName(), user.getFirstName());
    assertEquals(registerData.getLastName(), user.getLastName());
    assertEquals(registerData.getEmail().toLowerCase(), user.getEmail());

    verify(this.userRepository, times(1)).findByEmail(anyString());
    verify(this.roleRepository, times(1)).findByName(any());
  }

  @ParameterizedTest
  @ArgumentsSource(LoginDTOProviderTest.class)
  void shouldDeleteUser(LoginDTO loginDTO) {
    BDDMockito.given(this.userRepository.findByEmail(loginDTO.getEmail().toLowerCase()))
        .willReturn(Optional.of(this.user));
    BDDMockito.doNothing().when(this.userRepository).delete(this.user);

    this.service.delete(loginDTO);

    verify(this.userRepository, times(1)).findByEmail(anyString());
    verify(this.userRepository, times(1)).delete(any(User.class));
  }
}
