package com.gearservice.controller;

import com.gearservice.model.user.User;
import com.gearservice.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/api/user")
    public Principal user(Principal user) {return user;}

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<User> getUsers() {return userRepository.findAll();}

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public List<User> setUsers(@RequestBody List<User> users) {
        return userRepository.save(users);
    }

}
