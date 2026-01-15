package com.backendlld.hospitalManagement.model;

import com.backendlld.hospitalManagement.model.enums.AuthProviderType;
import com.backendlld.hospitalManagement.model.enums.RoleType;
import com.backendlld.hospitalManagement.security.RolePermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_provider_id_provider_type", columnList = "providerId,providerType")
})
public class User extends Base implements UserDetails{
    @JoinColumn(unique = true, nullable = false)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)//As we have to build seperate table for roles we have added this annotation.
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles=new HashSet<>();

    private String providerId;

    private boolean loggedIn;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());

//        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
//        roles.forEach(
//                role -> {
//                    Set<SimpleGrantedAuthority> permessions = RolePermissionMapping.getAuthoritiesForRole(role);
//                    authorities.addAll(permessions);
//                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
//                }
//        );
//        return authorities;
    }
}