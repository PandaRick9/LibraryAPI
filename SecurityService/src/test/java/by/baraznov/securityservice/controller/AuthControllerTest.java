package by.baraznov.securityservice.controller;

import by.baraznov.securityservice.dto.JwtAuthenticationResponse;
import by.baraznov.securityservice.dto.SignInRequest;
import by.baraznov.securityservice.dto.SignUpRequest;
import by.baraznov.securityservice.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void signUp_ValidRequest_ShouldReturnJwtResponse() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("user", "password");
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("jwtToken");

        when(authenticationService.signUp(signUpRequest)).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwtToken"));

        verify(authenticationService, times(1)).signUp(signUpRequest);
    }

    @Test
    void signIn_ValidRequest_ShouldReturnJwtResponse() throws Exception {
        SignInRequest signInRequest = new SignInRequest("user", "password");
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("jwtToken");

        when(authenticationService.signIn(signInRequest)).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwtToken"));

        verify(authenticationService, times(1)).signIn(signInRequest);
    }


}
