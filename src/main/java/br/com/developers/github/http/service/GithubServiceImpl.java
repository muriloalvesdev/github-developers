package br.com.developers.github.http.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import br.com.developers.github.http.service.request.Send;
import br.com.developers.github.http.utils.UrlUtils;

@Service
class GithubServiceImpl implements GithubService {

  private Send send;

  private String baseUrl;

  private String search;

  GithubServiceImpl(Send send, @Value("${github.base.url}") String baseUrl,
      @Value("${github.search}") String search) {
    this.send = send;
    this.baseUrl = baseUrl;
    this.search = search;
  }

  public ResponseEntity<Object> search(String endpoint, String qualifier, String sort, String order,
      int perPage, int page) {
    UriComponents url = UrlUtils.createUrl(endpoint, this.baseUrl, this.search, qualifier, sort,
        order, perPage, page);
    return this.send.sendRequest(url, HttpMethod.GET);
  }
}
