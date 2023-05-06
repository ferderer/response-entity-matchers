package de.fv.responseentitymatchers.matcher;

import static de.fv.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.AGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class HeaderMatchersTest {

    private static final ResponseEntity<?> ACCEPT_ALL = build(ACCEPT, ALL_VALUE);
    private static final ResponseEntity<?> ACCEPT_IMAGE = build(ACCEPT, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);
    private static final ResponseEntity<?> ACCEPT_JSON = build(ACCEPT, APPLICATION_JSON_VALUE);
    private static final ResponseEntity<?> ACCEPT_LANG_DE = build(ACCEPT_LANGUAGE, "de-DE");
    private static final ResponseEntity<?> AGE_42 = build(AGE, "42");
    private static final ResponseEntity<?> MULTIPLE = ResponseEntity.ok().header(ACCEPT, ALL_VALUE)
        .header(ACCEPT_LANGUAGE, "de").build();
    private static final Long EPOCH = 1679152320000L;
    private static final ResponseEntity<?> DATE_2023 = build(DATE, "Sat, 18 Mar 2023 15:12:00 GMT");

    private static ResponseEntity<?> build(String name, String... values) {
        return ResponseEntity.ok().header(name, values).build();
    }

    @Test
    public void testSingleMatcher() throws Exception {
        header().string(ACCEPT, is(ALL_VALUE)).match(ACCEPT_ALL);
    }

    @Test
    public void failWithWrongValue() throws Exception {
        assertThrows(AssertionError.class, () -> header().string(ACCEPT, is(ALL_VALUE)).match(ACCEPT_JSON));
    }

    @Test
    public void failWithWrongName() throws Exception {
        assertThrows(AssertionError.class, () -> header().string(ACCEPT, is(ALL_VALUE)).match(ACCEPT_LANG_DE));
    }

    @Test
    public void testMultipleMatcher() throws Exception {
        header().stringValues(ACCEPT, equalTo(Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE))).match(ACCEPT_IMAGE);
    }

    @Test
    public void failMultipleMatcher() throws Exception {
        assertThrows(AssertionError.class, () -> {
            header().stringValues(ACCEPT, is(Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE))).match(ACCEPT_JSON);
        });
    }

    @Test
    public void testStringMatcher() throws Exception {
        header().string(ACCEPT, ALL_VALUE).match(ACCEPT_ALL);
    }

    @Test
    public void failStringMatcher() throws Exception {
        assertThrows(AssertionError.class, () -> header().string(ACCEPT, ALL_VALUE).match(ACCEPT_JSON));
    }

    @Test
    public void testMultipleStringMatcher() throws Exception {
        header().stringValues(ACCEPT, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).match(ACCEPT_IMAGE);
    }

    @Test
    public void failMultipleStringMatcher() throws Exception {
        assertThrows(AssertionError.class, () -> header().stringValues(ACCEPT, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).match(ACCEPT_JSON));
    }

    @Test
    public void testExistingHeader() throws Exception {
        header().exists(ACCEPT).match(MULTIPLE);
    }

    @Test
    public void failMissingHeader() throws Exception {
        assertThrows(AssertionError.class, () -> header().exists(AUTHORIZATION).match(MULTIPLE));
    }

    @Test
    public void testMissingHeader() throws Exception {
        header().doesNotExist(AGE).match(MULTIPLE);
    }

    @Test
    public void failExistingHeader() throws Exception {
        assertThrows(AssertionError.class, () -> header().doesNotExist(ACCEPT).match(MULTIPLE));
    }

    @Test
    public void testLongMatcher() throws Exception {
        header().longValue(AGE, 42L).match(AGE_42);
    }

    @Test
    public void failLongMatcherWithWrongValue() throws Exception {
        assertThrows(AssertionError.class, () -> header().longValue(AGE, 1L).match(AGE_42));
    }

    @Test
    public void testDateMatcher() throws Exception {
        header().dateValue(DATE, EPOCH).match(DATE_2023);
    }

    @Test
    public void failDateMatcherWithWrongValue() throws Exception {
        assertThrows(AssertionError.class, () -> header().dateValue(DATE, 42L).match(DATE_2023));
    }
}
