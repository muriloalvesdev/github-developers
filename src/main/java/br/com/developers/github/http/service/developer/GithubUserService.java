package br.com.developers.github.http.service.developer;

import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

public interface GithubUserService<T> {
  ResponseEntity<T> search(@NotNull String qualifier, String sort, String order, int perPage,
      int page);
}
