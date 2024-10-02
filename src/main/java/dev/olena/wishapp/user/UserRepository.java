package dev.olena.wishapp.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
