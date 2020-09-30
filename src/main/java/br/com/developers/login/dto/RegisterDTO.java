package br.com.developers.login.dto;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDTO {
  @NotNull(message = "name is required")
  private String name;

  @NotNull(message = "last_name is required")
  @JsonProperty("last_name")
  private String lastName;

  @Email(message = "email is invalid")
  private String email;

  @NotNull(message = "role is required")
  private Set<String> role;

  @NotNull(message = "password is required")
  private String password;
}
