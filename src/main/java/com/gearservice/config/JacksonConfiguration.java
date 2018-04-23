package com.gearservice.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Class JacksonConfiguration is configuration
 * Add to Jackson mapper Hibernate4Module for correct JSON serialization and deserialization
 * of Hibernate specific datatypes and properties; especially lazy-loading aspects.
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

@Configuration
class JacksonConfiguration {

    @SuppressWarnings("unchecked")
    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(Hibernate5Module.class);
    }

}
