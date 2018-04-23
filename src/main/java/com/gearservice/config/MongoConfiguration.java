package com.gearservice.config;

import com.gearservice.config.converter.OffsetDateTimeToStringConverter;
import com.gearservice.config.converter.StringToOffsetDateTimeConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Collections.singletonList;

/**
 * Class MongoConfiguration is configuration
 * Config for mongoDB, take parameters from application.properties.
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
@EnableMongoRepositories(basePackages = {"com.gearservice.repositories.mongo"})
class MongoConfiguration extends AbstractMongoConfiguration {

    private final MongoProperties properties;

    @Autowired
    public MongoConfiguration(MongoProperties properties) {
        this.properties = properties;
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new OffsetDateTimeToStringConverter(),
                new StringToOffsetDateTimeConverter()));
    }

    @Override
    public String getDatabaseName() {return Optional.ofNullable(properties.getDatabase()).orElse("test");}

    @Override
    public MongoClient mongoClient() {
        String host = Optional.ofNullable(properties.getHost()).orElse("localhost");
        Integer port = Optional.ofNullable(properties.getPort()).orElse(27017);
        String database = Optional.ofNullable(properties.getDatabase()).orElse("test");
        Optional<String> username = Optional.ofNullable(properties.getUsername());
        Optional<char[]> password = Optional.ofNullable(properties.getPassword());

        if (username.isPresent() && password.isPresent()) {
            return new MongoClient(
                    singletonList(new ServerAddress(host, port)),
                    MongoCredential.createCredential(username.get(), database, password.get()),
                    new MongoClientOptions.Builder().build()
            );
        } else {
            return new MongoClient(singletonList(new ServerAddress(host, port)));
        }
    }
}