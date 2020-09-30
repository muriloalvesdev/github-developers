package br.com.developers.domain.model;

import java.util.Arrays;
import br.com.developers.login.exception.IllegalRoleException;

public enum RoleName {
  ROLE_ADMIN("admin");

  private static final String ROLE_INVALID = "Fail! -> Cause: %s is Role invalid.";
  private String name;

  private RoleName(String name) {
    this.name = name;
  }

  private String getName() {
    return this.name;
  }

  public static RoleName find(String roleName) {
    return Arrays.asList(RoleName.values()).stream()
        .filter(r -> r.getName().equals(roleName.toLowerCase())).findAny()
        .orElseThrow(() -> new IllegalRoleException(String.format(ROLE_INVALID, roleName)));
  }
}
