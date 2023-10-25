package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.DevRodUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevRodUserRepository extends JpaRepository<DevRodUser, Integer> {
    DevRodUser findByUsername(String username);
}
