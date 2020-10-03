package br.com.developers.github.http.service.request.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import br.com.developers.github.http.service.request.Send;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class SendImpl implements Send {
  private RestTemplate restTemplate;

  public ResponseEntity<Object> sendRequest(UriComponents url, HttpMethod httpMethod) {
    log.info("Sending request to URI [ " + url + " ]");
    HttpHeaders headers = createHttpHeaders();
    HttpEntity<Object> entity = createHttpEntity(headers);
    String uri = url.toUriString();
    return this.restTemplate.exchange(uri, httpMethod, entity, Object.class);
  }

  private HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("accept", "application/vnd.github.v3+json");
    return headers;
  }

  private HttpEntity<Object> createHttpEntity(HttpHeaders headers) {
    return new HttpEntity<Object>(headers);
  }

}
