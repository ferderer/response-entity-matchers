package de.ferderer.responseentitymatchers.matcher;

import org.hamcrest.Matcher;

public class MatcherFactory {

    /**
     * Access to response body assertions.
     */
    public static ContentMatchers content() {
        return new ContentMatchers();
    }

    /**
     * Access to response header assertions.
     */
    public static HeaderMatchers header() {
        return new HeaderMatchers();
    }

    /**
     * Access to response status assertions.
     */
    public static StatusMatchers status() {
        return new StatusMatchers();
    }

    /**
     * Access to response body assertions using a <a href="https://github.com/jayway/JsonPath">JsonPath</a>
     * expression to inspect a specific subset of the body. The JSON path expression can be a parameterized
     * string using formatting specifiers as defined in {@link String#format(String, Object[])}.
     */
    public static JsonPathMatchers jsonPath(String expression, Object... args) {
        return new JsonPathMatchers(expression, args);
    }

    /**
     * Evaluate the given <a href="https://github.com/jayway/JsonPath">JsonPath</a> expression
     * against the response body and assert the resulting value with the given Hamcrest {@link Matcher}.
     */
    public static <T> ResponseMatcher jsonPath(String expression, Matcher<T> matcher) {
        return new JsonPathMatchers(expression).value(matcher);
    }

    /**
     * Evaluate the given <a href="https://github.com/jayway/JsonPath">JsonPath</a>
     * expression against the response body and assert the resulting value with
     * the given Hamcrest {@link Matcher}, coercing the resulting value into the
     * given target type before applying the matcher.
     *
     * This can be useful for matching numbers reliably &mdash; for example, to coerce an integer into a double.
     */
    public static <T> ResponseMatcher jsonPath(String expression, Matcher<T> matcher, Class<T> targetType) {
        return new JsonPathMatchers(expression).value(matcher, targetType);
    }

    private MatcherFactory() {}
}
