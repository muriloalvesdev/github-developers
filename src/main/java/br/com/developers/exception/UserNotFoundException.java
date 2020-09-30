package br.com.developers.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String msg) {
    super(msg);
  }
}
