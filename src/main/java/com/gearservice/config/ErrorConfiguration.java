package com.gearservice.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
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
    public EmbeddedServletContainerCustomizer notFoundCustomizer(){return new NotFoundIndexTemplate();}

    private static class NotFoundIndexTemplate implements EmbeddedServletContainerCustomizer {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        }
    }

}
