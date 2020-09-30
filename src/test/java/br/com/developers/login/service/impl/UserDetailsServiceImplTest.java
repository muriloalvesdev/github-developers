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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import br.com.developers.constants.UserConstantsForTests;
import br.com.developers.login.domain.model.Role;
import br.com.developers.login.domain.model.Role.RoleName;
import br.com.developers.login.domain.model.User;
import br.com.developers.login.domain.repository.UserRepository;
import br.com.developers.login.exception.EmailNotFoundException;

class UserDetailsServiceImplTest implements UserConstantsForTests {

  private UserRepository repository;
  private UserDetailsServiceImpl service;

  private User user;

  @BeforeEach
  void setUp() {
    HashSet<Role> roles = new HashSet<>();
    roles.add(Role.newBuilder().id(1L).name(RoleName.ROLE_ADMIN).build());

    this.user = User.newBuilder().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
        .roles(roles).build();

    this.repository = spy(UserRepository.class);
    this.service = new UserDetailsServiceImpl(this.repository);
  }

  @ParameterizedTest
  @ValueSource(strings = {EMAIL})
  void shouldUserByUsername(String email) {
    BDDMockito.given(this.repository.findByEmail(email)).willReturn(Optional.of(this.user));

    this.service.loadUserByUsername(email);

    verify(this.repository, times(1)).findByEmail(anyString());
  }

  @ParameterizedTest
  @ValueSource(strings = {""})
  void shouldExpectedEmailNotFoundExceptionWithEmailEmpty(String email) {
    BDDMockito.given(this.repository.findByEmail(email)).willReturn(Optional.empty());

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.loadUserByUsername(email);
    });

    assertEquals("User Not Found with -> email : ", exception.getMessage());
    assertTrue(exception instanceof EmailNotFoundException);

    verify(this.repository, times(1)).findByEmail(anyString());
  }

  @Test
  void shouldExpectedEmailNotFoundExceptionWithEmailNull() {
    BDDMockito.given(this.repository.findByEmail(null)).willReturn(Optional.empty());

    Exception exception = assertThrows(Exception.class, () -> {
      this.service.loadUserByUsername(null);
    });

    assertEquals("User Not Found with -> email : null", exception.getMessage());
    assertTrue(exception instanceof EmailNotFoundException);

    verify(this.repository, times(1)).findByEmail(any());
  }

}
