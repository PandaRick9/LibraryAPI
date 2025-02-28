package by.baraznov.securityservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int statusCode;
}
