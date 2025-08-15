package com.springsecurity.SecurityApp.SecurityApp.utils;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Permission;
import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.springsecurity.SecurityApp.SecurityApp.entities.enums.Permission.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> roleMappings = Map.of(
            Role.USER,Set.of(USER_VIEW, POST_VIEW),
            Role.CREATOR, Set.of(POST_CREATE,POST_UPDATE,POST_VIEW, USER_UPDATE),
            Role.ADMIN, Set.of(POST_CREATE, POST_UPDATE, POST_VIEW,POST_DELETE,
                    USER_CREATE,USER_UPDATE,USER_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return roleMappings.get(role).stream().map(permission -> {
            return new SimpleGrantedAuthority(permission.name());
        }).collect(Collectors.toSet());
    }
}
