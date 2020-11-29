package br.com.developers.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface UserConstantsForTests {
  static final ObjectMapper MAPPER = new ObjectMapper();
  static final String PATH = "/api/user/";
  static final String PATH_LOGIN = "/api/auth/";
  static final String EMAIL = "murilohenrique.ti@outlook.com.br";
  static final String FIRST_NAME = "Murilo";
  static final String LAST_NAME = "Batista";
  static final String PASSWORD = "123456";
  static final String TOKEN =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
}
