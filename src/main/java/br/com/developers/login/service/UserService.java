package br.com.developers.login.service;

public interface UserService<R, L, U, D, T> {
  U registerUser(R dto);

  T authenticateUser(L dto);

  U update(R dto);

  void delete(L dto);

  D find(String id);
}
