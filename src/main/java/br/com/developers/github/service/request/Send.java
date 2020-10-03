package br.com.developers.github.service.request;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;

public interface Send {
  ResponseEntity<Object> sendRequest(UriComponents url, HttpMethod httpMethod);
}
