package com.strawHat.backend.repository;

import com.strawHat.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data access for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Finds a user by their email address (used as the login username). */
    Optional<User> findByEmail(String email);

    /** Checks whether a user with the given email already exists. */
    boolean existsByEmail(String email);
}
