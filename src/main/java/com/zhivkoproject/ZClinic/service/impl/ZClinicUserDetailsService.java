package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public class ZClinicUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public ZClinicUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByUsername(username).map(this::map).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    private UserDetails map(User user) {

        return new ZClinicUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getSurname(),
                user.getUserRoles().
                        stream().
                        map(this::map).
                        toList()

        );

    }

    private GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole().name());
    }
}
