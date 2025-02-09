package by.baraznov.securityservice.service;


import by.baraznov.securityservice.model.User;
import by.baraznov.securityservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
    }

    @Test
    void save_ShouldReturnSavedUser() {
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(user);
        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void create_ShouldReturnCreatedUser_WhenUserDoesNotExist() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.create(user);
        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        verify(userRepository, times(1)).existsByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void create_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.create(user));
        assertEquals("User with this username already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByUsername(user.getUsername());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getByUsername_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User foundUser = userService.getByUsername(user.getUsername());
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void getByUsername_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(user.getUsername()));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void userDetailsService_ShouldReturnUserDetailsService() {
        UserDetailsService userDetailsService = userService.userDetailsService();
        assertNotNull(userDetailsService);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(user, userDetailsService.loadUserByUsername(user.getUsername()));
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }
}
