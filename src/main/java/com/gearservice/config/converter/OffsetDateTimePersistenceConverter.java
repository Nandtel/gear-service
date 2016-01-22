package com.gearservice.config.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetDateTime;

/**
 * Class OffsetDateTimePersistenceConverter is configuration for converter between OffsetDateTime and String
 * Used for converting between Java 8 OffsetDateTime and JSON String
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

@Converter(autoApply = true)
public class OffsetDateTimePersistenceConverter implements AttributeConverter<OffsetDateTime, String> {

    @Override
    public String convertToDatabaseColumn(OffsetDateTime entityValue) {
        return entityValue != null ? entityValue.toString() : null;
    }

    @Override
    public OffsetDateTime convertToEntityAttribute(String databaseValue) {
        return databaseValue != null ? OffsetDateTime.parse(databaseValue) : null;
    }
}