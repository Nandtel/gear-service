package com.gearservice.config.converter;


import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;

/**
 * Class OffsetDateTimeToStringConverter is configuration for converter OffsetDateTime to String
 * Used for converting Java 8 OffsetDateTime to JSON String
 * Only for MongoBD
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

public class OffsetDateTimeToStringConverter implements Converter<OffsetDateTime, String> {

    @Override
    public String convert(OffsetDateTime entityValue) {
        return entityValue != null ? entityValue.toString() : null;

    }
}