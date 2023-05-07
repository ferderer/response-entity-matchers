package de.ferderer.responseentitymatchers.matcher;

import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ContentMatchersTest {
    private static final String ATEXT = "Aliquam venenatis urna eu ultrices convallis";
    private static final String BTEXT = "Phasellus vestibulum tellus ante";

    private static final String AJSON = "{\"firstname\":\"John\",\"lastname\":\"Doe\"}";
    private static final String RJSON = "{\"lastname\":\"Doe\",\"firstname\":\"John\"}";
    private static final String BJSON = "{\"firstname\":\"Jane\",\"lastname\":\"Doe\"}";

    private static final ResponseEntity<?> RE_ATEXT = build(ATEXT, MediaType.TEXT_PLAIN);
    private static final ResponseEntity<?> RE_BTEXT = build(BTEXT, MediaType.TEXT_PLAIN);
    private static final ResponseEntity<?> RE_AJSON = build(AJSON, MediaType.APPLICATION_JSON);
    private static final ResponseEntity<?> RE_BJSON = build(BJSON, MediaType.APPLICATION_JSON);
    private static final ResponseEntity<?> RE_RJSON = build(RJSON, MediaType.APPLICATION_JSON);

    private static ResponseEntity<?> build(String body, MediaType contentType) {
        return ResponseEntity.ok().contentType(contentType).body(body);
    }

    @Test
    public void stringContentMatcherShouldSucceed() throws Exception {
        content().string(ATEXT).match(RE_ATEXT);
    }

    @Test
    public void stringContentMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().string(ATEXT).match(RE_BTEXT));
    }

    @Test
    public void hamcrestContentMatcherShouldSucceed() throws Exception {
        content().string(is(ATEXT)).match(RE_ATEXT);
    }

    @Test
    public void hamcrestContentMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().string(is(ATEXT)).match(RE_BTEXT));
    }

    @Test
    public void contentTypeStringMatcherShouldSucceed() throws Exception {
        content().contentType(MediaType.TEXT_PLAIN_VALUE).match(RE_ATEXT);
    }

    @Test
    public void contentTypeStringMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().contentType(MediaType.TEXT_HTML_VALUE).match(RE_BTEXT));
    }

    @Test
    public void contentTypeMediaTypeMatcherShouldSucceed() throws Exception {
        content().contentType(MediaType.TEXT_PLAIN).match(RE_ATEXT);
    }

    @Test
    public void contentTypeMediaTypeMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().contentType(MediaType.TEXT_HTML).match(RE_ATEXT));
    }

    @Test
    public void contentTypeCompatibilityMatcherShouldSucceed() throws Exception {
        content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN).match(RE_ATEXT);
    }

    @Test
    public void contentTypeCompatibilityMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().contentTypeCompatibleWith(MediaType.TEXT_HTML).match(RE_ATEXT));
    }

    @Test
    public void jsonContentMatcherShouldSucceed() throws Exception {
        content().json(AJSON).match(RE_AJSON);
    }

    @Test
    public void jsonReshuffledContentMatcherShouldSucceed() throws Exception {
        content().json(AJSON).match(RE_RJSON);
    }

    @Test
    public void jsonContentMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().json(AJSON).match(RE_BJSON));
    }

    @Test
    public void jsonStrictContentMatcherShouldSucceed() throws Exception {
        content().json(AJSON, true).match(RE_AJSON);
    }

    @Test
    public void jsonStrictReshuffledContentMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().json(BJSON, true).match(RE_AJSON));
    }

    @Test
    public void jsonStrictContentMatcherShouldFail() throws Exception {
        assertThrows(AssertionError.class, () -> content().json(AJSON, true).match(RE_BJSON));
    }
}
