package com.gearservice.controller;

import com.gearservice.model.authorization.User;
import com.gearservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Class UserController is controller, that handle requests of users.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/api/user")
    public Principal user(Principal user) {
        logger.info("User " + user.getName().toUpperCase() + " has logged in");
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public List<User> getUsers() {return userService.getUserList();}

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void setUser(@RequestBody User user) {userService.saveUser(user);}

}
