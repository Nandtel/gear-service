package com.gearservice.config.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.OffsetDateTime;

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