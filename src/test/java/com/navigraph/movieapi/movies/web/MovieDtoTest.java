package com.navigraph.movieapi.movies.web;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class MovieDtoTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(MovieDto.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}
