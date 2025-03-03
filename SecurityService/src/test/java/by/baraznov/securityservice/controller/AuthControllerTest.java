package by.baraznov.securityservice.controller;

import by.baraznov.securityservice.controller.AuthController;
import by.baraznov.securityservice.dto.JwtAuthenticationResponse;
import by.baraznov.securityservice.dto.SignInRequest;
import by.baraznov.securityservice.dto.SignUpRequest;
import by.baraznov.securityservice.service.AuthenticationService;
import by.baraznov.securityservice.util.ErrorResponse;
import by.baraznov.securityservice.util.UserAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;

    @BeforeEach
    public void setUp() {
        signUpRequest = new SignUpRequest("username", "password");
        signInRequest = new SignInRequest("username", "password");
    }

    @Test
    public void testSignUp_Success() throws UserAlreadyExists {
        when(authenticationService.signUp(any(SignUpRequest.class))).thenReturn(new JwtAuthenticationResponse("token"));
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = authController.signUp(signUpRequest, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationService, times(1)).signUp(any(SignUpRequest.class));
    }

    @Test
    public void testSignUp_UserAlreadyExists() throws UserAlreadyExists {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authenticationService.signUp(any(SignUpRequest.class))).thenThrow(new UserAlreadyExists("User already exists"));

        ResponseEntity<?> response = authController.signUp(signUpRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testSignUp_BindingResultError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("signUpRequest", "username", "Username is required");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<?> response = authController.signUp(signUpRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("username - Username is required;", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testSignIn_Success() {
        when(authenticationService.signIn(any(SignInRequest.class))).thenReturn(new JwtAuthenticationResponse("token"));
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = authController.signIn(signInRequest, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationService, times(1)).signIn(any(SignInRequest.class));
    }

    @Test
    public void testSignIn_InternalAuthenticationServiceException() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authenticationService.signIn(any(SignInRequest.class))).thenThrow(new InternalAuthenticationServiceException("Invalid credentials"));

        ResponseEntity<?> response = authController.signIn(signInRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid credentials", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testSignIn_BindingResultError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("signInRequest", "password", "Password is required");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ResponseEntity<?> response = authController.signIn(signInRequest, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("password - Password is required;", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    public void testSignIn_InternalServerError() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authenticationService.signIn(any(SignInRequest.class))).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = authController.signIn(signInRequest, bindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", ((ErrorResponse) response.getBody()).getMessage());
    }
}