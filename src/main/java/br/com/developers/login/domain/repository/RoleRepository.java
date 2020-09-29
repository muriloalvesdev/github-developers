package br.com.developers.login.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.developers.login.domain.model.Role;
import br.com.developers.login.domain.model.Role.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName roleName);

}
