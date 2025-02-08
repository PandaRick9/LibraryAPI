package by.baraznov.securityservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for user registration (sign-up)")
public class SignUpRequest {
    @Schema(description = "Desired username for the new user", example = "john_doe")
    private String username;

    @Schema(description = "Password for the new user", example = "securepassword123")
    private String password;
}