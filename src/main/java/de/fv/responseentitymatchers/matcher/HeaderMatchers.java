package de.fv.responseentitymatchers.matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import java.util.Arrays;
import org.hamcrest.Matcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Factory for response header assertions. An instance of this
 * class is typically accessed via {@link ResultMatchers#header()}.
 *
 * @author Vadim Ferderer
 * @since 1.0
 */
public class HeaderMatchers {

    HeaderMatchers() {}

    /**
     * Assert the primary value of the response header with the given Hamcrest String {@code Matcher}.
     */
    public ResponseMatcher string(String name, Matcher<? super String> matcher) {
        return result -> assertThat("Response header '" + name + "'", header(result, name), matcher);
    }

    /**
     * Assert the values of the response header with the given Hamcrest Iterable {@link Matcher}.
     */
    public ResponseMatcher stringValues(String name, Matcher<Iterable<String>> matcher) {
        return result -> assertThat("Response header '" + name + "'", headers(result, name), matcher);
    }

    /**
     * Assert the primary value of the response header as a String value.
     */
    public ResponseMatcher string(String name, String value) {
        return result -> assertEquals("Response header '" + name + "'", value, header(result, name));
    }

    /**
     * Assert the values of the response header as String values.
     */
    public ResponseMatcher stringValues(String name, String... values) {
        return result -> assertEquals("Response header '" + name + "'", Arrays.asList(values), headers(result, name));
    }

    /**
     * Assert that the named response header exists.
     */
    public ResponseMatcher exists(String name) {
        return result -> assertTrue("Response should contain header '" + name + "'", has(result, name));
    }

    /**
     * Assert that the named response header does not exist.
     */
    public ResponseMatcher doesNotExist(String name) {
        return result -> assertFalse("Response should not contain header '" + name + "'", has(result, name));
    }

    /**
     * Assert the primary value of the named response header as a {@code long}.
     *
     * The {@link ResponseMatcher} returned by this method throws an
     * {@link AssertionError} if the response does not contain the specified
     * header, or if the supplied {@code value} does not match the primary value.
     */
    public ResponseMatcher longValue(String name, long value) {
        return result -> {
            assertTrue("Response does not contain header '" + name + "'", has(result, name));
            String headerValue = header(result, name);
            if (headerValue != null) {
                assertEquals("Response header '" + name + "'", value, Long.valueOf(headerValue));
            }
        };
    }

    /**
     * Assert the primary value of the named response header parsed into a date
     * using the preferred date format described in RFC 7231.
     * <p>The {@link ResponseMatcher} returned by this method throws an
     * {@link AssertionError} if the response does not contain the specified
     * header, or if the supplied {@code value} does not match the primary value.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    public ResponseMatcher dateValue(String name, long value) {
        return result -> {
            String headerValue = header(result, name);
            assertNotNull("Response does not contain header '" + name + "'", headerValue);

            HttpHeaders headers = new HttpHeaders();
            headers.setDate("expected", value);
            headers.set("actual", headerValue);

            assertEquals("Response header '" + name + "'='" + headerValue + "' " +
                            "does not match expected value '" + headers.getFirst("expected") + "'",
                    headers.getFirstDate("expected"), headers.getFirstDate("actual"));
        };
    }

    private String header(ResponseEntity<?> response, String name) {
        return response.getHeaders().getFirst(name);
    }

    private Iterable<String> headers(ResponseEntity<?> response, String name) {
        return response.getHeaders().get(name);
    }

    private boolean has(ResponseEntity<?> response, String name) {
        return response.getHeaders().containsKey(name);
    }
}
