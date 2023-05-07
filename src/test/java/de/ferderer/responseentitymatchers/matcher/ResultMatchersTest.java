package de.ferderer.responseentitymatchers.matcher;

import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResultMatchersTest {
    private static final String JSON = "{\"str\": \"foo\", \"utf8Str\": \"PÅ™Ã­liÅ¡\", \"num\": 5.2, \"bool\": true, \"arr\""
        + ": [42], \"colorMap\": {\"red\": \"rojo\"}, \"emptyString\": \"\", \"emptyArray\": [], \"emptyMap\": {}}";
    private static final ResponseEntity<?> RE = re(JSON, MediaType.APPLICATION_JSON);

    private static ResponseEntity<?> re(String body, MediaType contentType) {
        return ResponseEntity.ok().contentType(contentType).body(body);
    }

    @Test
    public void valueWithMismatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str", is("bogus")).match(RE));
    }

    @Test
    public void valueWithDirectMatch() throws Exception {
        jsonPath("$.str", is("foo")).match(RE);
    }

    @Test
    public void utf8ValueWithDirectMatch() throws Exception {
        jsonPath("$.utf8Str", is("PÅ™Ã­liÅ¡")).match(RE);
    }

    @Test
    public void valueWithNumberConversion() throws Exception {
        jsonPath("$.num", is(5.2)).match(RE);
    }

    @Test
    public void valueWithMatcher() throws Exception {
        jsonPath("$.str", equalTo("foo")).match(RE);
    }

    @Test
    public void valueWithMatcherAndNumberConversion() throws Exception {
        jsonPath("$.num", equalTo(5.2f), Float.class).match(RE);
    }

    @Test
    public void valueWithMatcherAndMismatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str", is("bogus")).match(RE));
    }
}
