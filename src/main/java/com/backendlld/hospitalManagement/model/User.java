package com.backendlld.hospitalManagement.model;

import com.backendlld.hospitalManagement.model.enums.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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

    private String providerId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}