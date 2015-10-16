package com.gearservice.service;

import com.gearservice.model.authorization.Authorities;
import com.gearservice.model.authorization.User;
import com.gearservice.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService, UserDetailsManager {

    @Autowired UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        return new UserDetailsImpl(user);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    private final static class UserDetailsImpl extends User implements UserDetails {

        private UserDetailsImpl(User user) {super(user);}

        public String getFullName() {return super.getFullName();}
        @Override public String getPassword() {return super.getPassword();}
        @Override public String getUsername() {return super.getUsername();}
        @Override public boolean isAccountNonExpired() {return true;}
        @Override public boolean isAccountNonLocked() {return true;}
        @Override public boolean isCredentialsNonExpired() {return true;}
        @Override public boolean isEnabled() {return super.isEnabled();}
        @Override public Set<Authorities> getAuthorities() {return super.getAuthorities();}

    }
}