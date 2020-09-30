package br.com.developers.controller.user;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.developers.domain.model.User;
import br.com.developers.login.dto.RegisterDTO;
import br.com.developers.login.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {

  private UserService userService;

  @PostMapping("/")
  public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterDTO registerData) {
    User user = userService.registerUser(registerData);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(user.getId()).toUri()).build();

  }

}
