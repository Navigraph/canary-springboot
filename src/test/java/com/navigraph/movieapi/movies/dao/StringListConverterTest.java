package com.navigraph.movieapi.movies.dao;

import org.junit.jupiter.api.Test;
import com.navigraph.movieapi.utils.StringListConverter;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class StringListConverterTest {

    private StringListConverter stringListConverter = new StringListConverter();

    @Test
    void givenEmptyList_whenConvertingToDatabaseColumn_shouldReturnEmptyString() {
        assertThat(stringListConverter.convertToDatabaseColumn(new ArrayList<>())).isEmpty();
    }

    @Test
    void givenNullList_whenConvertingToDatabaseColumn_shouldReturnEmptyString() {
        assertThat(stringListConverter.convertToDatabaseColumn(null)).isEmpty();
    }

    @Test
    void givenList_whenConvertingToDatabaseColumn_shouldReturnJoinedString() {
        assertThat(stringListConverter.convertToDatabaseColumn(asList("Value1", "Value2", "Value3")))
                .isEqualTo("Value1|Value2|Value3");
    }

    @Test
    void givenEmptyString_whenConvertingToEntityAttribute_shouldReturnLEmptyList() {
        assertThat(stringListConverter.convertToEntityAttribute("")).isEmpty();
    }

    @Test
    void givenNullString_whenConvertingToEntityAttribute_shouldReturnEmptyList() {
        assertThat(stringListConverter.convertToEntityAttribute(null)).isEmpty();
    }

    @Test
    void givenSeparatedString_whenConvertingToEntityAttribute_shouldList() {
        assertThat(stringListConverter.convertToEntityAttribute("Value1|Value2|Value3")).containsExactlyInAnyOrder("Value1", "Value2", "Value3");
    }
}
