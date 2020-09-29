package br.com.developers.login.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessToken {

  @JsonProperty("access_token")
  private String accessToken;

}
