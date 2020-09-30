package br.com.developers.login.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
  @Email(message = "email is invalid")
  private String email;

  @NotNull(message = "password is required")
  private String password;
}
