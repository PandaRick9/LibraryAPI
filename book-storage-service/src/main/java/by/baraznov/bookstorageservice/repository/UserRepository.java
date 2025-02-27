package by.baraznov.bookstorageservice.repository;


import by.baraznov.bookstorageservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * UserRepository provides database access methods for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Finds a user by username.
     * @param username The username to search for
     * @return An optional containing the found user or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with a given username exists.
     * @param username The username to check
     * @return true if the user exists, false otherwise
     */
    boolean existsByUsername(String username);
}