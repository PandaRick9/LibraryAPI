package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.model.User;
import by.baraznov.bookstorageservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
        InternalAuthenticationServiceException exception = assertThrows(InternalAuthenticationServiceException.class, () -> userService.getByUsername(user.getUsername()));
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
