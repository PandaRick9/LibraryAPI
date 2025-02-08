package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.db2.model.User;
import by.baraznov.bookstorageservice.db2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Saves a user
     *
     * @return saved user
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Creates a new user
     *
     * @return created user
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
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
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

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