package com.navigraph.movieapi.utils;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings == null || strings.isEmpty() ? "" : String.join("|", strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return StringUtils.isEmpty(string) ? new ArrayList<>() : Arrays.asList(string.split("\\|"));
    }
}
