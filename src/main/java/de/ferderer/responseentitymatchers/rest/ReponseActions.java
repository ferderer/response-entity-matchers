package de.ferderer.responseentitymatchers.rest;

import de.ferderer.responseentitymatchers.handler.ResponseHandler;
import de.ferderer.responseentitymatchers.matcher.ResponseMatcher;
import org.springframework.http.ResponseEntity;

public class ReponseActions {

    private final ResponseEntity<?> response;

    public ReponseActions(ResponseEntity<?> response) {
        this.response = response;
    }

    /**
     * Performs on assertion on the response entity.
     */
    public ReponseActions andExpect(ResponseMatcher matcher) throws Exception {
        matcher.match(response);
        return this;
    }

    /**
     * Executes an action on the response entity.
     */
    public ReponseActions andDo(ResponseHandler handler) throws Exception {
        handler.handle(response);
        return this;
    }

    /**
     * Returns the response entity for further examination​​.
     */
    public ResponseEntity<?> andReturn() {
        return response;
    }
}
