package br.com.developers.controller.user.login;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.developers.domain.model.User;
import br.com.developers.login.dto.LoginDTO;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.http.request.AccessToken;
import br.com.developers.login.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
public class LoginController {

  private UserService<RegisterDTO, LoginDTO, User> userService;

  @PostMapping("/")
  public ResponseEntity<AccessToken> authenticate(@Validated @RequestBody LoginDTO loginData) {
    return ResponseEntity.ok().body(userService.authenticateUser(loginData));
  }
}
