package de.fv.responseentitymatchers.matcher;

import java.io.UnsupportedEncodingException;
import org.hamcrest.Matcher;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.JsonPathExpectationsHelper;

/**
 * Factory for assertions on the request content using
 * <a href="https://github.com/jayway/JsonPath">JsonPath</a> expressions.
 *
 * <p>An instance of this class is typically accessed via
 * {@link ResultMatchers#jsonPath(String, Matcher)} or
 * {@link ResultMatchers#jsonPath(String, Object...)}.
 *
 * @author Vadim Ferderer
 * @since 1.0
 */
public class JsonPathMatchers {

    private final JsonPathExpectationsHelper jsonPathHelper;

    JsonPathMatchers(String expression, Object... args) {
        this.jsonPathHelper = new JsonPathExpectationsHelper(expression, args);
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert the resulting value with the given Hamcrest {@link Matcher}.
     */
    @SuppressWarnings("unchecked")
    public <T> ResponseMatcher value(Matcher<T> matcher) {
        return result -> jsonPathHelper.assertValue(getContent(result), matcher);
    }

    /**
     * An overloaded variant of {@link #value(Matcher)} that also accepts a target
     * type for the resulting value that the matcher can work reliably against.
     * This can be useful for matching numbers reliably &mdash; for example, to coerce an integer into a double.
     */
    @SuppressWarnings("unchecked")
    public <T> ResponseMatcher value(Matcher<T> matcher, Class<T> targetType) {
        return result -> this.jsonPathHelper.assertValue(getContent(result), matcher, targetType);
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is equal to the supplied value.
     */
    public ResponseMatcher value(Object expectedValue) {
        return result -> this.jsonPathHelper.assertValue(getContent(result), expectedValue);
    }

    /**
     * Evaluate the JSON path expression against the response content and assert that a non-null value,
     * possibly an empty array or map, exists at the given path. If the JSON path expression is not
     * {@linkplain JsonPath#isDefinite definite}, this method asserts that the value at the given path
     * is not  <em>empty</em>.
     */
    public ResponseMatcher exists() {
        return result -> this.jsonPathHelper.exists(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that a non-null value does not exist at the given path.
     *
     * If the JSON path expression is not {@linkplain JsonPath#isDefinite
     * definite}, this method asserts that the value at the given path is
     * <em>empty</em>.
     */
    public ResponseMatcher doesNotExist() {
        return result -> this.jsonPathHelper.doesNotExist(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that an empty value exists at the given path.
     *
     * For the semantics of <em>empty</em>, consult the Javadoc for
     * {@link org.springframework.util.ObjectUtils#isEmpty(Object)}.
     */
    public ResponseMatcher isEmpty() {
        return result -> this.jsonPathHelper.assertValueIsEmpty(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that a non-empty value exists at the given path.
     *
     * For the semantics of <em>empty</em>, consult the Javadoc for
     * {@link org.springframework.util.ObjectUtils#isEmpty(Object)}.
     */
    public ResponseMatcher isNotEmpty() {
        return result -> this.jsonPathHelper.assertValueIsNotEmpty(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content
     * and assert that a value, possibly {@code null}, exists.
     *
     * If the JSON path expression is not
     * {@linkplain JsonPath#isDefinite() definite}, this method asserts
     * that the list of values at the given path is not <em>empty</em>.
     */
    public ResponseMatcher hasJsonPath() {
        return result -> this.jsonPathHelper.hasJsonPath(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the supplied {@code content}
     * and assert that a value, including {@code null} values, does not exist
     * at the given path.
     *
     * If the JSON path expression is not
     * {@linkplain JsonPath#isDefinite() definite}, this method asserts
     * that the list of values at the given path is <em>empty</em>.
     */
    public ResponseMatcher doesNotHaveJsonPath() {
        return result -> this.jsonPathHelper.doesNotHaveJsonPath(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is a {@link String}.
     */
    public ResponseMatcher isString() {
        return result -> this.jsonPathHelper.assertValueIsString(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is a {@link Boolean}.
     */
    public ResponseMatcher isBoolean() {
        return result -> this.jsonPathHelper.assertValueIsBoolean(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is a {@link Number}.
     */
    public ResponseMatcher isNumber() {
        return result -> this.jsonPathHelper.assertValueIsNumber(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is an array.
     */
    public ResponseMatcher isArray() {
        return result -> this.jsonPathHelper.assertValueIsArray(getContent(result));
    }

    /**
     * Evaluate the JSON path expression against the response content and
     * assert that the result is a {@link java.util.Map}.
     */
    public ResponseMatcher isMap() {
        return result -> this.jsonPathHelper.assertValueIsMap(getContent(result));
    }

    @SuppressWarnings("unchecked")
    private String getContent(HttpEntity<?> result) throws UnsupportedEncodingException {
        return ((HttpEntity<String>) result).getBody();
    }
}
