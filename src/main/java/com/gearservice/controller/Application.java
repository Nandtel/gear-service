package com.gearservice.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Class Application is controller, that handles all request from client-side
 * Use @Autowired for connect to necessary repositories and Entity manager
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@RestController
public class Application {

//    // Match everything without a suffix (so not a static resource)
//    @RequestMapping(value = "/{[path:[^\\.]*}")
//    public ModelAndView redirect() {
//        // Forward to home page so that route is preserved.
//        return new ModelAndView("forward:/");
//    }

}
