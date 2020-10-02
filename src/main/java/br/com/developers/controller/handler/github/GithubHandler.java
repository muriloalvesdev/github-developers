package br.com.developers.controller.handler.github;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import br.com.developers.github.http.resource.developer.Developer;
import br.com.developers.github.http.service.developer.GithubUserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class GithubHandler {

  private GithubUserService<Developer> service;

  public ResponseEntity<Developer> searchUser(String qualifier, String sort, String order,
      int perPage, int page) {
    return this.service.search(qualifier, sort, order, perPage, page);
  }
}
