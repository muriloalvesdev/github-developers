package br.com.developers.login.service;

import br.com.developers.login.dto.UserDTO;
import br.com.developers.login.http.request.AccessToken;

public interface UserService<R, L, U> {
  U registerUser(R dto);

  AccessToken authenticateUser(L dto);

  U update(R dto);

  void delete(L dto);

  UserDTO find(String id);
}
