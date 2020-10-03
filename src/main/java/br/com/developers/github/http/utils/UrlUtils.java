package br.com.developers.github.http.utils;

import java.net.URI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlUtils {

  public static UriComponents createUrl(String endpoint, String baseUrl, String search,
      String qualifier, String sort, String order, int perPage, int page) {
    UriComponents uri = addParams(endpoint, baseUrl, search, qualifier, sort, order, perPage, page);
    return uri;
  }

  private static UriComponents addParams(String endpoint, String baseUrl, String search,
      String qualifier, String sort, String order, int perPage, int page) {
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

    builder.uri(URI.create(baseUrl.concat(search).concat(endpoint)));

    builder.queryParam("q", qualifier);
    if (validateParameter(sort)) {
      builder.queryParam("sort", sort);
    }

    if (validateParameter(order)) {
      builder.queryParam("order", order);
    }

    if (validateParameter(String.valueOf(perPage))) {
      builder.queryParam("per_page", String.valueOf(perPage));
    }

    if (validateParameter(String.valueOf(page))) {
      builder.queryParam("page", String.valueOf(page));
    }
    return builder.build(true);
  }

  private static boolean validateParameter(String parameter) {
    return StringUtils.isNotBlank(parameter);
  }
}
