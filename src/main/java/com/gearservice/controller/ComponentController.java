package com.gearservice.controller;

import com.gearservice.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class ComponentController is controller, that handle requests of components.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class ComponentController {

    @Autowired ComponentService componentService;

    @RequestMapping(value = "/api/au/component/{itemName}", method = RequestMethod.GET)
    public List<String> autocompleteData(@PathVariable String itemName) {
        return componentService.getAutocompleteData(itemName);}


}
