package org.dides.studentmanagement.model;

import org.dides.studentmanagement.enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserPrincipal implements UserDetails {

    private final Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    // ✅ expose the underlying entity + helpers for SpEL / controllers
    public Users getUser() { return user; }
    public Long getId()    { return user.getId(); }
    public Roles getRole() { return user.getRole(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Roles role = user.getRole();
        if (role == null) return java.util.List.of();

        Stream<String> roleAuth = Stream.of("ROLE_" + role.name());
        Stream<String> permAuth = role.getPermissions().stream().map(Enum::name);

        return Stream.concat(roleAuth, permAuth)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override public String getPassword()              { return user.getPassword(); }
    @Override public String getUsername()              { return user.getEmail(); }
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
