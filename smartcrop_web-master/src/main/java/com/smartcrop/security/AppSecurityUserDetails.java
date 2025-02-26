package com.smartcrop.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartcrop.entity.SmartCropUser;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Configuration
@NoArgsConstructor
@ToString(exclude = {"password"})
public class AppSecurityUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String userId;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public AppSecurityUserDetails(Long id, String username, String userId, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.authorities = authorities;
    }

    public static AppSecurityUserDetails build(SmartCropUser user) {
        List<GrantedAuthority> authorities = Arrays.asList(user.getRole()).stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new AppSecurityUserDetails(
                user.getId(),
                user.getUserName(),
                user.getUserId(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
