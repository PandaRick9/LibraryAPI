package by.baraznov.securityservice.controller;


import by.baraznov.securityservice.dto.SignInRequest;
import by.baraznov.securityservice.dto.SignUpRequest;
import by.baraznov.securityservice.service.AuthenticationService;
import by.baraznov.securityservice.util.ErrorResponse;
import by.baraznov.securityservice.util.UserAlreadyExists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth_method")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "User registration",
            description = "Registers a new user and returns a JWT authentication response."
    )
    @PostMapping("/signup")
    public ResponseEntity<?>  signUp(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signUp(request));
        }catch (UserAlreadyExists ex){
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "User authentication",
            description = "Authenticates a user and returns a JWT authentication response."
    )
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signIn(request));
        }catch (InternalAuthenticationServiceException ex){
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            ex.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}