package com.github.nvans.utils.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

/**
 * Allow use the java8 LocalDate class
 * with JPA entities.
 *
 */
@Converter
public class LocalDatePersistenceConverter
                            implements AttributeConverter<LocalDate, java.sql.Date> {


    @Override
    public java.sql.Date convertToDatabaseColumn(LocalDate localDate) {

        if (localDate != null) {
            return java.sql.Date.valueOf(localDate);
        }

        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(java.sql.Date dbData) {

        if (dbData != null) {
            return dbData.toLocalDate();
        }

        return null;
    }
}
