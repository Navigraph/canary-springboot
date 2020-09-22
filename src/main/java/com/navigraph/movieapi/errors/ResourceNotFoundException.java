package com.navigraph.movieapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 266853955330077478L;

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("Unable to find resource [%s] with id [%d]", resourceName, id));
    }
}
