package br.com.developers.github.http.resource.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Items {
  @JsonProperty
  private String login;

  @JsonProperty
  private int id;

  @JsonProperty("avatar_url")
  private String avatar;

  @JsonProperty
  private String url;

  @JsonProperty("html_url")
  private String htmlUrl;

  @JsonProperty("organizations_url")
  private String organizationsUrl;

  @JsonProperty("repos_url")
  private String reposUrl;
}
