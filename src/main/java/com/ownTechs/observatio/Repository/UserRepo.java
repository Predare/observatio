package com.ownTechs.observatio.Repository;

import java.util.Optional;

import com.ownTechs.observatio.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
      Optional<User> findByUsername(String username);
}
