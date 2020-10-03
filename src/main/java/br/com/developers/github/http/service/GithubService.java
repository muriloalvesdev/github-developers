package br.com.developers.github.http.service;

import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

public interface GithubService {
  ResponseEntity<Object> search(@NotNull String endpoint, @NotNull String qualifier, String sort,
      String order, int perPage, int page);
}
