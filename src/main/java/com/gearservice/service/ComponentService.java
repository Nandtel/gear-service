package com.gearservice.service;

import com.gearservice.repositories.jpa.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class ComponentService is service, that handle ComponentController.
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class ComponentService {

    @Autowired ComponentRepository componentRepository;

    /**
     * Method getAutocompleteData return autocomplete data by request
     * @param itemName is type of autocomplete that are necessary
     * @return list of string, that contains autocomplete data
     */
    @Transactional(readOnly = true)
    public List<String> getAutocompleteData(String itemName) {return componentRepository.listOfComponentNames();}
}
