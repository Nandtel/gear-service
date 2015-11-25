package com.gearservice.controller;

import com.gearservice.model.request.AnalyticsPreferences;
import com.gearservice.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalyticsController {

    @Autowired AnalyticsService analyticsService;

//    @RequestMapping(value = "/api/analytics", method = RequestMethod.POST)
//    public Map<String, Map<String, Double>> getAnalytics(@RequestBody AnalyticsPreferences analyticsPreferences) {
//        return analyticsService.getAnalytics(analyticsPreferences);
////        return analyticsService.getAnalytics(analyticsPreferences);
//    }

//    @RequestMapping(value = "/api/analytics", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//    public ResponseEntity<InputStreamResource> getAnalytics(@RequestBody AnalyticsPreferences analyticsPreferences) throws Exception {
//        return analyticsService.getExcelFile(analyticsPreferences);
//    }

    @RequestMapping(value = "/api/analytics", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public byte[] getAnalytics(@RequestBody AnalyticsPreferences analyticsPreferences) throws Exception {
        return analyticsService.getExcelFile(analyticsPreferences);
    }

}
