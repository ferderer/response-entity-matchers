package de.ferderer.responseentitymatchers.handler;

import org.springframework.http.ResponseEntity;

/**
 * A {@code ResponseHandler} performs a generic action on the response enity of the executed request.
 *
 * <p>See static factory methods in {@link de.ferderer.responseentitymatchers.matcher.MatcherFactory}
 *
 * <b>Example</b>
 *
 * <pre class="code">
 * import static de.ferderer.responseentitymatchers.rest.requestbuilder.*;
 * import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
 * // ...
 * new RequestBuilder(client, HttpMethod.GET, "/form").perform().andDo(print());
 * </pre>
 *
 * @author Vadim Ferderer
 * @since 1.0
 */
@FunctionalInterface
public interface ResponseHandler {

    /**
     * Performs an action on the response entity.
     */
    void handle(ResponseEntity<?> result) throws Exception;
}
