package br.com.developers.exception;

public class ExistingEmailException extends RuntimeException {

  public ExistingEmailException() {
    super("Failed -> Email is already in use!");
  }

}
