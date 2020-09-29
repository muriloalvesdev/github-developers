package br.com.developers.login.exception;

public class EmailNotFoundException extends RuntimeException {

  public EmailNotFoundException(String msg) {
    super(msg);
  }

}
