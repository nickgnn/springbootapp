package my.example.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import my.example.springbootapp.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    Optional<User> findIdByUsername(String name);
}
