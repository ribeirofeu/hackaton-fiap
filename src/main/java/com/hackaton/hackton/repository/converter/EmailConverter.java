package com.hackaton.hackton.repository.converter;



import com.hackaton.hackton.model.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute != null ? attribute.getAddress() : null;
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData != null ? Email.of(dbData) : null;
    }
}
