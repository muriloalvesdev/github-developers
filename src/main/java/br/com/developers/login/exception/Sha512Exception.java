package br.com.developers.login.exception;

public class Sha512Exception extends RuntimeException {

  public Sha512Exception(String msg) {
    super("Error ao gerar SHA512, error: " + msg);
  }

}
