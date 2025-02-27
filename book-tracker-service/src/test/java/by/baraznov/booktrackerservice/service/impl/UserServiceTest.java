package by.baraznov.booktrackerservice.service.impl;


import by.baraznov.booktrackerservice.model.User;
import by.baraznov.booktrackerservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password");
    }

    @Test
    public void testGetByUsername_UserExists() {
        when(repository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        User result = userService.getByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(repository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testUserDetailsService() {
        when(repository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        UserDetailsService userDetailsService = userService.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(repository, times(1)).findByUsername("testUser");
    }

}
