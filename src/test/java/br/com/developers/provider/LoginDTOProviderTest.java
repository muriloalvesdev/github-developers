package br.com.developers.provider;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import br.com.developers.constants.UserConstantsForTests;
import br.com.developers.login.dto.LoginDTO;

public class LoginDTOProviderTest implements ArgumentsProvider, UserConstantsForTests {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

    LoginDTO dto = new LoginDTO();
    dto.setEmail(EMAIL);
    dto.setPassword(PASSWORD);
    return Stream.of(dto).map(Arguments::of);
  }


}
