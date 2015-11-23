package com.gearservice.controller;

import com.gearservice.model.request.AnalyticsPreferences;
import com.gearservice.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AnalyticsController {

    @Autowired AnalyticsService analyticsService;

    @RequestMapping(value = "/api/analytics", method = RequestMethod.POST)
    public Map<String, Map<String, Double>> getAnalytics(@RequestBody AnalyticsPreferences analyticsPreferences) {
        return analyticsService.getAnalytics(analyticsPreferences);
    }

}
