package com.ownTechs.observatio.DTO.Users;

import com.ownTechs.observatio.Security.Role;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Builder
@Data
public class UserDefaultDetails implements UserDetails {

    private Integer id;
    private String username;
    private String password;

    private Boolean enabled;

    private ArrayList<Role> authorities = new ArrayList<Role>();
    
    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
}
