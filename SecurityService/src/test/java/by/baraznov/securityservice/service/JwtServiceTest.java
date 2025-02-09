package by.baraznov.securityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private String token;
    private String signingKey = "dGVzdC1zaWduaW5nLWtleS10ZXN0LXNpZ25pbmcta2V5LXRlc3Q=";

    @BeforeEach
    void setUp() {
        jwtService.setJwtSigningKey(signingKey);
        token = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void extractUserName_ShouldReturnUserName() {
        String username = jwtService.extractUserName(token);
        assertEquals("testUser", username);
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenExpired_ShouldReturnFalseForValidToken() {
        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    void extractExpiration_ShouldReturnCorrectExpirationDate() {
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void extractClaim_ShouldReturnCorrectClaim() {
        Function<Claims, String> claimResolver = Claims::getSubject;
        String claim = jwtService.extractUserName(token);
        assertEquals("testUser", claimResolver.apply(Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()));
    }
}