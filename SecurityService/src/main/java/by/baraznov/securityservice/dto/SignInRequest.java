package by.baraznov.securityservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for user authentication (sign-in)")
public class SignInRequest {
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Password of the user", example = "securepassword123")
    private String password;
}