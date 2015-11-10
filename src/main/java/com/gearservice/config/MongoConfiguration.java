package com.gearservice.config;


import com.gearservice.config.converter.OffsetDateTimeToStringConverter;
import com.gearservice.config.converter.StringToOffsetDateTimeConverter;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = {"com.gearservice.model.repositories"})
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    public CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(
                new OffsetDateTimeToStringConverter(),
                new StringToOffsetDateTimeConverter()));
    }

    @Override
    protected String getDatabaseName() {
        return "photographies";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1");
    }
}