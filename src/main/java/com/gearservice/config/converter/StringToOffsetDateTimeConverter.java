package com.gearservice.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;

public class StringToOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(String databaseValue) {
        return databaseValue != null ? OffsetDateTime.parse(databaseValue) : null;

    }

}
