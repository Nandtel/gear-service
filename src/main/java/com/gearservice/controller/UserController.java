package com.gearservice.controller;

import com.gearservice.model.authorization.User;
import com.gearservice.model.repositories.UserRepository;
import com.gearservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired UserService userService;

    @RequestMapping("/api/user")
    public Principal user(Principal user) {
        logger.info("User " + user.getName().toUpperCase() + " has logged in");
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<User> getUsers() {return userService.getUserList();}

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void setUser(@RequestBody User user) {userService.saveUser(user);}

    @RequestMapping(value = "/api/autocomplete/user/{itemName}", method = RequestMethod.GET)
    public List<String> autocompleteData(@PathVariable String itemName) {return userService.getAutocompleteData(itemName);}


}
