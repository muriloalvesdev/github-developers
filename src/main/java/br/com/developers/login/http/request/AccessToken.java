package br.com.developers.login.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccessToken {

  @JsonProperty("access_token")
  private String accessToken;

}
