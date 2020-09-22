package com.navigraph.movieapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Chose BAD_REQUEST to be compatible with Spring, would otherwise favor UNPROCESSABLE_ENTITY
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidResourceException extends RuntimeException{

    public InvalidResourceException() {
    }

    public InvalidResourceException(String message) {
        super(message);
    }
}
