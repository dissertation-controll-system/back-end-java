package com.masterswork.account.api.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.masterswork.account.model.enumeration.PersonType;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;

public class StringToPersonTypeEnumConverter implements Converter<String, PersonType> {

    @Override
    public PersonType convert(String source) {
        try {
            return StringUtils.hasText(source) ?
                    PersonType.valueOf(source.trim().toUpperCase()) : null;
        } catch (Exception e) {
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(PersonType.class), source, e);
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(PersonType.class);
    }

}