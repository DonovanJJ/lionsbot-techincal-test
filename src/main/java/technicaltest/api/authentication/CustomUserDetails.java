package technicaltest.api.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import technicaltest.api.user.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private UUID uuid;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UUID uuid, String username, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
