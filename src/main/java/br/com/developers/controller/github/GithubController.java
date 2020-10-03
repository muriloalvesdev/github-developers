package br.com.developers.controller.github;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.developers.github.http.service.developer.GithubService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(GithubController.PATH)
public class GithubController {

  protected static final String PATH = "/api/github/";

  private GithubService service;

  @GetMapping("search/{endpoint}")
  public ResponseEntity<Object> search(@PathVariable(name = "endpoint") String endpoint,
      @RequestParam(name = "qualifier") String qualifier,
      @RequestParam(name = "sort", required = false) String sort,
      @RequestParam(name = "order", required = false) String order,
      @RequestParam(name = "per_page", required = false, defaultValue = "10") int perPage,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
    return service.search(endpoint, qualifier, sort, order, perPage, page);
  }

}
