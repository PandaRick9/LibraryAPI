package by.baraznov.bookstorageservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@Schema(description = "User Entity")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "user id", example = "1")
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    @Schema(description = "user name", example = "Иван")
    private String username;

    @Column(name = "password", nullable = false)
    @Schema(description = "user password", example = "1234")
    private String password;

    /**
     * Returns the authorities granted to the user.
     * @return A collection of granted authorities (currently null).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Checks if the account is expired.
     * @return true (account never expires).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the account is locked.
     * @return true (account is not locked).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the credentials are expired.
     * @return true (credentials never expire).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the account is enabled.
     * @return true (account is enabled).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}