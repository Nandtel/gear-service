package com.gearservice.controller;

import com.gearservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class ApplicationController is controller, that handle main requests.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

//    // Match everything without a suffix (so not a static resource)
//    @RequestMapping(value = "/{[path:[^\\.]*}")
//    public ModelAndView redirect1() {
//        // Forward to home page so that route is preserved.
//        return new ModelAndView("forward:/");
//    }

    @RequestMapping(value = "/api/sample", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void makeSample() {applicationService.makeSample();}

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void test() {}

    @RequestMapping(value = "/api/autocomplete/{itemName}/{searchText:.+}", method = RequestMethod.GET)
    public List<String> autocompleteData(@PathVariable("itemName") String itemName, @PathVariable("searchText") String searchText) {
        return applicationService.getAutocompleteData(itemName, searchText);}
}
