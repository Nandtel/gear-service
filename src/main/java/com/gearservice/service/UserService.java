package com.gearservice.service;

import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Service
public class UserService implements UserDetailsService {

    @Autowired UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOne(username);
        if(user == null) throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        return new UserDetailsImpl(user);
    }

    public List<User> getUserList() {return userRepository.findAll();}

    public void saveUser(User user) {

        if (userRepository.exists(user.getUsername())) {

            User userFromDB = userRepository.findOne(user.getUsername());

            if (user.getNewPassword() == null) {
                user.setPassword(userFromDB.getPassword());
            }
            else if (user.getAdminPassword() != null &&
                    passwordEncoder().matches(user.getAdminPassword(), userRepository.findOne("parinov_a").getPassword())) {
                String password = user.getNewPassword();
                user.setPassword(passwordEncoder().encode(password));
            }
        } else {
            String password = user.getNewPassword();
            user.setPassword(passwordEncoder().encode(password));
        }

        userRepository.save(user);
    }

    private final static class UserDetailsImpl extends User implements UserDetails {

        private UserDetailsImpl(User user) {super(user);}

        public String getFullname() {return super.getFullname();}
        @Override public String getPassword() {return super.getPassword();}
        @Override public String getUsername() {return super.getUsername();}
        @Override public boolean isAccountNonExpired() {return true;}
        @Override public boolean isAccountNonLocked() {return true;}
        @Override public boolean isCredentialsNonExpired() {return true;}
        @Override public boolean isEnabled() {return super.isEnabled();}
        @Override public Set<Authority> getAuthorities() {return super.getAuthorities();}
    }

    public Map<String, String> getUsernameFullnameMap() {
        return userRepository.findAll()
                .stream()
                .collect(toMap(User::getUsername, User::getFullname));
    }

    public List<String> getAutocompleteData(String itemName) {
        switch (itemName) {
            case "secretaryName": return userRepository.listOfSecretaries();
            case "engineerName": return userRepository.listOfEngineers();
            default: throw new IllegalArgumentException();
        }
    }
}