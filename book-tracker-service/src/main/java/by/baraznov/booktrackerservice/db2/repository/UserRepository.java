package by.baraznov.booktrackerservice.db2.repository;



import by.baraznov.booktrackerservice.db2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by username.
     * @param username The username to search for.
     * @return An optional containing the found user or empty if not found.
     */
    Optional<User> findByUsername(String username);

}
