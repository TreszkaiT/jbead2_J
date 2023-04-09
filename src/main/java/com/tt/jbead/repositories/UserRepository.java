package com.tt.jbead.repositories;

import com.tt.jbead.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //    Optional<User> findByEmailAndPassword(String email, String pass);
    Optional<User> findByEmail(String email);
}
