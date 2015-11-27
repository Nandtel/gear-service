package com.gearservice.service;

import com.gearservice.model.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentService {

    @Autowired ComponentRepository componentRepository;

    public List<String> getAutocompleteData(String itemName) {
        return componentRepository.listOfComponentNames();
    }
}
