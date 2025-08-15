package com.springsecurity.SecurityApp.SecurityApp.entities;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Role;
import com.springsecurity.SecurityApp.SecurityApp.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.stream()
                .forEach(role -> {
                    authorities.addAll(PermissionMapping.getAuthoritiesForRole(role));
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
                });

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
