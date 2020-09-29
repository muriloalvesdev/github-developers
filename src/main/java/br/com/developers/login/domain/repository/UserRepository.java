package br.com.developers.login.domain.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.developers.login.domain.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Boolean existsByEmail(String email);
}
