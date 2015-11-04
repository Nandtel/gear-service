package com.gearservice.controller;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired AnalyticsService analyticsService;

    @RequestMapping(value = "/r", method = RequestMethod.GET)
    public String request() {
        System.out.println();
        System.out.println();
        System.out.println();
        analyticsService.run();
        System.out.println();
        System.out.println();
        System.out.println();

        return "All fine";
    }

}
