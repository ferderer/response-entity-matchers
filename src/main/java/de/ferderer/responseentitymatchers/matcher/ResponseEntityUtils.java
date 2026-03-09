package de.ferderer.responseentitymatchers.matcher;

import org.springframework.http.HttpEntity;

/**
 * Shared utilities for extracting data from response entities.
 *
 * @author Vadim Ferderer
 * @since 1.1
 */
final class ResponseEntityUtils {

    /**
     * Extract the response body as a String.
     */
    static String getBody(HttpEntity<?> result) {
        Object body = result.getBody();
        return body != null ? body.toString() : null;
    }

    private ResponseEntityUtils() {}
}
