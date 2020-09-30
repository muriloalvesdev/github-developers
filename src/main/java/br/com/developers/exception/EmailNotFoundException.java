package br.com.developers.exception;

public class EmailNotFoundException extends RuntimeException {

  public EmailNotFoundException(String msg) {
    super(msg);
  }

}
