package br.com.dev.connectly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dev.connectly.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

}
