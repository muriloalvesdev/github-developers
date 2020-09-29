package br.com.developers.login.exception;

public class ExistingEmailException extends RuntimeException {

  public ExistingEmailException() {
    super("Failed -> Email is already in use!");
  }

}
