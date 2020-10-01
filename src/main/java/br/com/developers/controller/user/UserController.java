package br.com.developers.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.developers.domain.model.User;
import br.com.developers.login.dto.LoginDTO;
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
  public ResponseEntity<Object> registerUser(@Validated @RequestBody RegisterDTO registerData) {
    User user = this.userService.registerUser(registerData);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(user.getId()).toUri()).build();
  }

  @PutMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> update(@Validated @RequestBody RegisterDTO registerData) {
    User user = this.userService.updateUser(registerData);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(user.getId()).toUri()).build();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> find(@RequestParam(name = "id") String id) {
    return ResponseEntity.ok(this.userService.find(id));
  }

  @DeleteMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> delete(@Validated @RequestBody LoginDTO loginDTO) {
    this.userService.delete(loginDTO);
    return ResponseEntity.noContent().build();
  }

}
