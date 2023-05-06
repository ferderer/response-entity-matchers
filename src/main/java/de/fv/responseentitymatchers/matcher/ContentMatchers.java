package de.fv.responseentitymatchers.matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import java.io.UnsupportedEncodingException;
import org.hamcrest.Matcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.util.JsonExpectationsHelper;

/**
 * Factory for response entity content matchers. An instance of this
 * class is typically accessed via {@link ResultMatchers#content()}.
 *
 * @author Vadim Ferderer
 * @since 1.0
 */
public class ContentMatchers {

    private final JsonExpectationsHelper jsonHelper;

    /**
	 * Protected constructor, not for direct instantiation.
	 * Use {@link ResultMatchers#content()}.
	 */
    ContentMatchers() {
        this.jsonHelper = new JsonExpectationsHelper();
    }

    /**
     * Assert the entity response content type.
     * The given content type must fully match including type, sub-type, and parameters.
     * For checking only the type and sub-type see {@link #contentTypeCompatibleWith(String)}.
     */
    public ResponseMatcher contentType(String contentType) {
        return contentType(MediaType.parseMediaType(contentType));
    }

    /**
     * Assert the ServletResponse content type after parsing it as a MediaType.
     * The given content type must fully match including type, sub-type, and parameters.
     * For checking only the type and sub-type see {@link #contentTypeCompatibleWith(MediaType)}.
     */
    public ResponseMatcher contentType(MediaType contentType) {
        return result -> assertEquals("Content type", contentType, result.getHeaders().getContentType());
    }

    /**
     * Assert the ServletResponse content type is compatible with the given
     * content type as defined by {@link MediaType#isCompatibleWith(MediaType)}.
     */
    public ResponseMatcher contentTypeCompatibleWith(String contentType) {
        return contentTypeCompatibleWith(MediaType.parseMediaType(contentType));
    }

    /**
     * Assert the ServletResponse content type is compatible with the given
     * content type as defined by {@link MediaType#isCompatibleWith(MediaType)}.
     */
    public ResponseMatcher contentTypeCompatibleWith(MediaType contentType) {
        return result -> {
            MediaType actual = result.getHeaders().getContentType();

            assertNotNull("Content type not set", actual);
            assertTrue("Content type [" + actual + "] is not compatible with [" + contentType + "]",
                    actual.isCompatibleWith(contentType));
        };
    }

    /**
     * Assert the body content with a Hamcrest {@link Matcher}.
     *
     * <pre class="code">
     * perform(get("/path"))
     *   .andExpect(content().string(containsString("text")));
     * </pre>
     */
    public ResponseMatcher string(Matcher<String> matcher) {
        return result -> assertThat("Response content", getContent(result), matcher);
    }

    /**
     * Assert the response body
     */
    public ResponseMatcher string(String expectedContent) {
        return result -> assertEquals("Response content", expectedContent, result.getBody());
    }

    /**
     * Parse the expected and actual strings as JSON and assert the two are "similar" - i.e.
     * they contain the same attribute-value pairs regardless of formatting with a lenient
     * checking (extensible, and non-strict array ordering).
     */
    public ResponseMatcher json(String jsonContent) {
        return json(jsonContent, false);
    }

    /**
     * Parse the response content and the given string as JSON and assert the two are "similar" -
     * i.e. they contain the same attribute-value pairs regardless of formatting.
     * Can compare in two modes, depending on {@code strict} parameter value:
     * <ul>
     *   <li>{@code true}: strict checking. Not extensible, and strict array ordering.</li>
     *   <li>{@code false}: lenient checking. Extensible, and non-strict array ordering.</li>
     * </ul>
     *
     * Usage of this matcher requires the <a href="https://jsonassert.skyscreamer.org/">JSONassert</a> library.
     */
    public ResponseMatcher json(String jsonContent, boolean strict) {
        return result -> jsonHelper.assertJsonEqual(jsonContent, getContent(result), strict);
    }

    @SuppressWarnings("unchecked")
    private String getContent(HttpEntity<?> result) throws UnsupportedEncodingException {
        return ((HttpEntity<String>) result).getBody();
    }
}
