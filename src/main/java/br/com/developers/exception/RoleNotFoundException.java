package br.com.developers.exception;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String msg) {
    super(msg);
  }
}
