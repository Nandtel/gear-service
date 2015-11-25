package com.gearservice.controller;

import com.gearservice.model.authorization.User;
import com.gearservice.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/api/user")
    public Principal user(Principal user) {return user;}

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<User> getUsers() {return userRepository.findAll();}

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void setUser(@RequestBody User user) {

        System.out.println(user.getNewPassword());
        System.out.println(user.getFullname());
        System.out.println(user.getUsername());

        if (user.getNewPassword() == null) {
            User userForReplacement = userRepository.findOne(user.getUsername());
            user.setPassword(userForReplacement.getPassword());
        } else {
            String password = user.getNewPassword();
            user.setPassword(passwordEncoder().encode(password));
        }

        userRepository.save(user);
    }

    @RequestMapping(value = "/api/user/{userID}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String userID) {userRepository.delete(userID);}

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
