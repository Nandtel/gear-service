package com.gearservice.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Class ErrorConfiguration is configuration
 * If page fire not found error return redirect to "/" path
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

@Configuration
class ErrorConfiguration {

    @Bean
    public WebServerFactoryCustomizer notFoundCustomizer(){return new NotFoundIndexTemplate();}

    private static class NotFoundIndexTemplate implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        }
    }

}
