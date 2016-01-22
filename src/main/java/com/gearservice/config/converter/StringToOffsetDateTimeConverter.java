package com.gearservice.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;

/**
 * Class StringToOffsetDateTimeConverter is configuration for converter String to OffsetDateTime
 * Used for converting JSON String to Java 8 OffsetDateTime
 * Only for MongoBD
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */


public class StringToOffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(String databaseValue) {
        return databaseValue != null ? OffsetDateTime.parse(databaseValue) : null;

    }

}
