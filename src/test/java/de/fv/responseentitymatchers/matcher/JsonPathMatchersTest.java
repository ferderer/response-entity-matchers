package de.fv.responseentitymatchers.matcher;

import static de.fv.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import static org.springframework.http.MediaType.*;

public class JsonPathMatchersTest {
    private static final String JSON = "{\"str\": \"foo\", \"utf8Str\": \"PÅ™Ã­liÅ¡\", \"num\": 5, \"bool\": true, \"arr\""
        + ": [42], \"colorMap\": {\"red\": \"rojo\"}, \"emptyString\": \"\", \"emptyArray\": [], \"emptyMap\": {}}";
    private static final ResponseEntity<?> RE = re(JSON, APPLICATION_JSON);

    private static ResponseEntity<?> re(String body, MediaType contentType) {
        return ResponseEntity.ok().contentType(contentType).body(body);
    }

    @Test
    public void valueWithMismatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").value("bogus").match(RE));
    }

    @Test
    public void valueWithDirectMatch() throws Exception {
        jsonPath("$.str").value("foo").match(RE);
    }

    @Test
    public void utf8ValueWithDirectMatch() throws Exception {
        jsonPath("$.utf8Str").value("PÅ™Ã­liÅ¡").match(RE);
    }

    @Test
    public void valueWithNumberConversion() throws Exception {
        jsonPath("$.num").value(5.0f).match(RE);
    }

    @Test
    public void valueWithMatcher() throws Exception {
        jsonPath("$.str").value(equalTo("foo")).match(RE);
    }

    @Test
    public void valueWithMatcherAndNumberConversion() throws Exception {
        jsonPath("$.num").value(equalTo(5.0f), Float.class).match(RE);
    }

    @Test
    public void valueWithMatcherAndMismatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").value(equalTo("bogus")).match(RE));
    }

    @Test
    public void exists() throws Exception {
        jsonPath("$.str").exists().match(RE);
    }

    @Test
    public void existsForAnEmptyArray() throws Exception {
        jsonPath("$.emptyArray").exists().match(RE);
    }

    @Test
    public void existsForAnEmptyMap() throws Exception {
        jsonPath("$.emptyMap").exists().match(RE);
    }

    @Test
    public void existsNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.bogus").exists().match(RE));
    }

    @Test
    public void doesNotExist() throws Exception {
        jsonPath("$.bogus").doesNotExist().match(RE);
    }

    @Test
    public void doesNotExistNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").doesNotExist().match(RE));
    }

    @Test
    public void doesNotExistForAnEmptyArray() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.emptyArray").doesNotExist().match(RE));
    }

    @Test
    public void doesNotExistForAnEmptyMap() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.emptyMap").doesNotExist().match(RE));
    }

    @Test
    public void isEmptyForAnEmptyString() throws Exception {
        jsonPath("$.emptyString").isEmpty().match(RE);
    }

    @Test
    public void isEmptyForAnEmptyArray() throws Exception {
        jsonPath("$.emptyArray").isEmpty().match(RE);
    }

    @Test
    public void isEmptyForAnEmptyMap() throws Exception {
        jsonPath("$.emptyMap").isEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForString() throws Exception {
        jsonPath("$.str").isNotEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForNumber() throws Exception {
        jsonPath("$.num").isNotEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForBoolean() throws Exception {
        jsonPath("$.bool").isNotEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForArray() throws Exception {
        jsonPath("$.arr").isNotEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForMap() throws Exception {
        jsonPath("$.colorMap").isNotEmpty().match(RE);
    }

    @Test
    public void isNotEmptyForAnEmptyString() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.emptyString").isNotEmpty().match(RE));
    }

    @Test
    public void isNotEmptyForAnEmptyArray() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.emptyArray").isNotEmpty().match(RE));
    }

    @Test
    public void isNotEmptyForAnEmptyMap() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.emptyMap").isNotEmpty().match(RE));
    }

    @Test
    public void isArray() throws Exception {
        jsonPath("$.arr").isArray().match(RE);
    }

    @Test
    public void isArrayForAnEmptyArray() throws Exception {
        jsonPath("$.emptyArray").isArray().match(RE);
    }

    @Test
    public void isArrayNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.bar").isArray().match(RE));
    }

    @Test
    public void isMap() throws Exception {
        jsonPath("$.colorMap").isMap().match(RE);
    }

    @Test
    public void isMapForAnEmptyMap() throws Exception {
        jsonPath("$.emptyMap").isMap().match(RE);
    }

    @Test
    public void isMapNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").isMap().match(RE));
    }

    @Test
    public void isBoolean() throws Exception {
        jsonPath("$.bool").isBoolean().match(RE);
    }

    @Test
    public void isBooleanNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").isBoolean().match(RE));
    }

    @Test
    public void isNumber() throws Exception {
        jsonPath("$.num").isNumber().match(RE);
    }

    @Test
    public void isNumberNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.str").isNumber().match(RE));
    }

    @Test
    public void isString() throws Exception {
        jsonPath("$.str").isString().match(RE);
    }

    @Test
    public void isStringNoMatch() throws Exception {
        assertThrows(AssertionError.class, () -> jsonPath("$.arr").isString().match(RE));
    }
}
