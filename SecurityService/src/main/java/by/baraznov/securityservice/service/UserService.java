package by.baraznov.securityservice.service;

import by.baraznov.securityservice.model.User;
import by.baraznov.securityservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Saves a user
     *
     * @return saved user
     */
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Creates a new user
     *
     * @return created user
     */
    @Transactional
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Replace with custom exceptions
            throw new RuntimeException("User with this username already exists");
        }

        return save(user);
    }

    /**
     * Retrieves a user by username
     *
     * @return user
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Retrieves a user by username
     * <p>
     * Required for Spring Security
     *
     * @return user
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


}
