package br.com.developers.github.http.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlUtils {

  public static UriComponents mountUrl(String baseUrl, String searchUsers, String qualifier,
      String sort, String order, int perPage, int page) {
    return UriComponentsBuilder.fromPath(baseUrl.concat(searchUsers).concat(qualifier))
        .queryParam("sort", validateParameter(sort)).queryParam("order", validateParameter(order))
        .queryParam("per_page", validateParameter(String.valueOf(perPage)))
        .queryParam("page", validateParameter(String.valueOf(page))).build();
  }

  private static String validateParameter(String parameter) {
    return StringUtils.isBlank(parameter) ? "" : parameter;
  }
}
