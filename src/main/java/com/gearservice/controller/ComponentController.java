package com.gearservice.controller;

import com.gearservice.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComponentController {

    @Autowired ComponentService componentService;

    @RequestMapping(value = "/api/au/component/{itemName}", method = RequestMethod.GET)
    public List<String> autocompleteData(@PathVariable String itemName) {
        return componentService.getAutocompleteData(itemName);}


}
