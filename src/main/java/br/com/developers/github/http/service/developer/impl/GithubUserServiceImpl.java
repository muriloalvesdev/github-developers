package br.com.developers.github.http.service.developer.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import br.com.developers.github.http.resource.developer.Developer;
import br.com.developers.github.http.service.developer.GithubUserService;
import br.com.developers.github.http.service.request.Send;
import br.com.developers.github.http.utils.UrlUtils;

@Service
public class GithubUserServiceImpl implements GithubUserService<Developer> {

  private Send<Developer> send;

  private String baseUrl;

  private String searchUsers;

  GithubUserServiceImpl(Send<Developer> send, @Value("${github.base.url}") String baseUrl,
      @Value("${github.search.users}") String searchUsers) {
    this.send = send;
    this.baseUrl = baseUrl;
    this.searchUsers = searchUsers;
  }

  public ResponseEntity<Developer> search(String qualifier, String sort, String order, int perPage,
      int page) {
    UriComponents uri =
        UrlUtils.mountUrl(this.baseUrl, this.searchUsers, qualifier, sort, order, perPage, page);
    return this.send.sendRequest(uri);
  }
}
