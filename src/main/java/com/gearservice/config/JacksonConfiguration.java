package com.gearservice.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(Hibernate4Module.class);
    }

}
