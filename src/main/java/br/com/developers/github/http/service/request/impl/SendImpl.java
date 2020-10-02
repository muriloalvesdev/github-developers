package br.com.developers.github.http.service.request.impl;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import br.com.developers.github.http.resource.developer.Developer;
import br.com.developers.github.http.service.request.Send;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class SendImpl implements Send<Developer> {

  private static final Logger LOG = Logger.getLogger(SendImpl.class);

  private RestTemplate restTemplate;

  public ResponseEntity<Developer> sendRequest(UriComponents uri) {
    LOG.info("Sending request to URI [ " + uri + " ]");
    HttpHeaders headers = createHttpHeaders();
    HttpEntity<Developer> entity = createHttpEntity(headers);
    return this.restTemplate.exchange(uri.toUri(), HttpMethod.POST, entity, Developer.class);
  }

  private HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", "application/vnd.github.v3+json");
    return headers;
  }

  private HttpEntity<Developer> createHttpEntity(HttpHeaders headers) {
    return new HttpEntity<Developer>(headers);
  }

}
