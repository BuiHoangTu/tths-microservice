package bhtu.work.tths.authservice.security.services;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bhtu.work.tths.authservice.models.User;

public class MyUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String username;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails (User user) {
        List<GrantedAuthority> authorities = user.getAccesses().stream()
                .map(access -> new SimpleGrantedAuthority(access.getAccess().name()))
                .collect(Collectors.toList());

        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetails oAsUserDetail)) return false;

        return Objects.equals(this.username, oAsUserDetail.getUsername());
    }

}
