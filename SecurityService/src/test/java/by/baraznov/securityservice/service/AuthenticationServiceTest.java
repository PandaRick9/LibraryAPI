package by.baraznov.securityservice.service;

import by.baraznov.securityservice.dto.JwtAuthenticationResponse;
import by.baraznov.securityservice.dto.SignInRequest;
import by.baraznov.securityservice.dto.SignUpRequest;
import by.baraznov.securityservice.model.User;
import by.baraznov.securityservice.service.AuthenticationService;
import by.baraznov.securityservice.service.JwtService;
import by.baraznov.securityservice.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void signUp_ValidRequest_ShouldReturnJwtResponse() {
        SignUpRequest request = new SignUpRequest("user", "password");
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signUp(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void signIn_ValidRequest_ShouldReturnJwtResponse() {
        SignInRequest request = new SignInRequest("user", "password");
        UserDetails userDetails = User.builder()
                .username("user")
                .password("encodedPassword")
                .build();
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(userService.userDetailsService()).thenReturn(userDetailsService);

        when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signIn(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());

        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        verify(userDetailsService, times(1)).loadUserByUsername(request.getUsername());
        verify(jwtService, times(1)).generateToken(userDetails);
    }

    @Test
    void signIn_InvalidCredentials_ShouldThrowException() {
        SignInRequest request = new SignInRequest("user", "wrongPassword");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        )).thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> authenticationService.signIn(request));

        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        verify(userService, never()).userDetailsService();
        verify(jwtService, never()).generateToken(any());
    }
}