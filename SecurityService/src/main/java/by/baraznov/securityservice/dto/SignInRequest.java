package by.baraznov.securityservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request object for user authentication (sign-in)")
public class SignInRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 2, max = 50, message = "Password must be between 2 and 50 characters")
    @Schema(description = "Password of the user", example = "securepassword123")
    private String password;
}