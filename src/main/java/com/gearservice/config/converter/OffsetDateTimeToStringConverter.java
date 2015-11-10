package com.gearservice.config.converter;


import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;

public class OffsetDateTimeToStringConverter implements Converter<OffsetDateTime, String> {

    @Override
    public String convert(OffsetDateTime entityValue) {
        return entityValue != null ? entityValue.toString() : null;

    }
}