package br.com.developers.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.developers.domain.model.Role;
import br.com.developers.domain.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName roleName);

}
