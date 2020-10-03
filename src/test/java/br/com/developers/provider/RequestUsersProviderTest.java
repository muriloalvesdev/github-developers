package br.com.developers.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

public class RequestUsersProviderTest implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(new Request("muriloalvesdev", "", "", 0, 10)).map(Arguments::of);
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @Data
  public class Request {
    private String qualifier;
    private String sort;
    private String order;
    private int perPage;
    private int page;
  }


}
