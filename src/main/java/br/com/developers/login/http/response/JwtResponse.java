package br.com.developers.login.http.response;

import lombok.Data;

@Data
public class JwtResponse {

  private String token;
  private String type = "Bearer";

}
