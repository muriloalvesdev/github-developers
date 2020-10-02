package br.com.developers.controller.github;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.developers.controller.handler.github.GithubHandler;
import br.com.developers.github.http.resource.developer.Developer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/github")
public class GithubController {

  private GithubHandler handler;

  @GetMapping("/search")
  public ResponseEntity<Developer> search(@RequestParam(name = "qualifier") String qualifier,
      @RequestParam(name = "sort", required = false) String sort,
      @RequestParam(name = "order", required = false) String order,
      @RequestParam(name = "per_page", required = false, defaultValue = "10") int perPage,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
    return handler.searchUser(qualifier, sort, order, perPage, page);
  }

}
