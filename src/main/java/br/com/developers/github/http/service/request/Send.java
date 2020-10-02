package br.com.developers.github.http.service.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;

public interface Send<T> {

  ResponseEntity<T> sendRequest(UriComponents uri);
}
