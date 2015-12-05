package com.gearservice.controller;

import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.model.cheque.*;
import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.model.repositories.*;
import com.gearservice.service.ApplicationService;
import com.gearservice.service.SampleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

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
public class ApplicationController {

    @Autowired ApplicationService applicationService;

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

    @RequestMapping(value = "/api/autocomplete/{itemName}", method = RequestMethod.GET)
    public List<?> autocompleteData(@PathVariable String itemName) {return applicationService.getAutocompleteData(itemName);}

}
