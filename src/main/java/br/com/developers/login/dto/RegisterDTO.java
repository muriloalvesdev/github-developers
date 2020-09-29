package br.com.developers.login.dto;

import java.util.Set;
import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterDTO {
  private String name;
  private String lastName;

  @Email
  private String email;
  private Set<String> role;
  private String password;
}
