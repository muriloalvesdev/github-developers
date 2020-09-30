package br.com.developers.login.service.impl;

import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import br.com.developers.domain.model.User;
import br.com.developers.domain.repository.UserRepository;
import br.com.developers.login.dto.UserDTO;
import br.com.developers.login.exception.EmailNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) {
    User user = this.userRepository.findByEmail(email)
        .orElseThrow(() -> new EmailNotFoundException("User Not Found with -> email : " + email));

    return UserDTO.build(user);
  }

}
