package br.com.developers.controller.user;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.developers.constants.UserConstantsForTests;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.http.request.AccessToken;
import br.com.developers.provider.LoginDTOProviderTest;
import br.com.developers.provider.RegisterDTOProviderTests;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerIntegrationTests implements UserConstantsForTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Order(1)
  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldRegisterWithSucess(RegisterDTO registerData) throws Exception {
    this.mockMvc
        .perform(post(PATH).content(createJsonRegisterData(registerData))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Order(2)
  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldTryRegisterAndReturnEmailExistingException(RegisterDTO registerData) throws Exception {
    this.mockMvc
        .perform(post(PATH).content(createJsonRegisterData(registerData))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message", is("Failed -> Email is already in use!")))
        .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.value())));
  }

  @Order(3)
  @ParameterizedTest
  @ArgumentsSource(LoginDTOProviderTest.class)
  void shouldTryLoginAndReturnUserNotFound(LoginDTO loginDTO) throws Exception {
    loginDTO.setEmail("teste@teste.com.br");
    this.mockMvc
        .perform(post(PATH_LOGIN).content(createJsonLoginDTO(loginDTO))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.message", is("User Not Found with -> email : teste@teste.com.br")))
        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())));
  }

  @Order(4)
  @ParameterizedTest
  @ArgumentsSource(RegisterDTOProviderTests.class)
  void shouldLoginAndUpdateUserWithSucess(RegisterDTO registerData) throws Exception {

    LoginDTO loginDTO = new LoginDTO();
    loginDTO.setEmail(EMAIL);
    loginDTO.setPassword(PASSWORD);

    String response = this.mockMvc
        .perform(post(PATH_LOGIN).content(createJsonLoginDTO(loginDTO))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    AccessToken token = MAPPER.convertValue(response, AccessToken.class);

    mockMvc
        .perform(put(PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createJsonRegisterData(registerData))
            .header("Authorization", "Bearer " + token.getAccessToken()))
        .andExpect(status().isCreated()).andExpect(header().exists("Location"));;
  }

  private String createJsonRegisterData(RegisterDTO registerData) throws JsonProcessingException {
    return MAPPER.writeValueAsString(registerData);
  }

  private String createJsonLoginDTO(LoginDTO loginDTO) throws JsonProcessingException {
    return MAPPER.writeValueAsString(loginDTO);
  }

}
