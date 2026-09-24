package br.com.dev.connectly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.connectly.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmailHash(String emailHash);

    Optional<Users> findByUsername(String username);
}