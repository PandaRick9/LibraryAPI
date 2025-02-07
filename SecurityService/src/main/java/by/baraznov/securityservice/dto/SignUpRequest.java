package by.baraznov.securityservice.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String password;
}