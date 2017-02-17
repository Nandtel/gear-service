package com.gearservice;

import com.gearservice.controller.AnalyticsController;
import com.gearservice.model.request.AnalyticsPreferences;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Class ControllerTests contains only unit-test without connection to Spring Context
 * for fast checking simple work of controller's methods
 *
 * @version 1.1
 * @author Dmitry
 * @since 23.01.2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ControllerTests {

    @Test
    public void AnalyticsControllerTest() throws Exception {
        AnalyticsController analyticsControllerMock = Mockito.mock(AnalyticsController.class);
        AnalyticsPreferences analyticsPreferences = new AnalyticsPreferences();
        analyticsControllerMock.getAnalytics(analyticsPreferences);

        verify(analyticsControllerMock, atLeastOnce()).getAnalytics(analyticsPreferences);
    }

}
