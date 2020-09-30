package br.com.developers.login.service;

import br.com.developers.domain.model.User;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.http.request.AccessToken;

public interface UserService {
  User registerUser(RegisterDTO registerData);

  AccessToken authenticateUser(LoginDTO loginDto);
}
