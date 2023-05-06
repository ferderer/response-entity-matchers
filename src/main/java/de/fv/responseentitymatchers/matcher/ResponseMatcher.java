package de.fv.responseentitymatchers.matcher;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface ResponseMatcher {

    /**
     * Performs an assertionon on the provided response entity.
     */
    void match(ResponseEntity<?> responseEntity) throws Exception;
}
