package org.example.individuelllabbloggbackend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String object, String field) {
        super(String.format("No %s found in database that matches the request: %s", object, field));
    }
}
