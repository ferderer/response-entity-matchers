package de.ferderer.responseentitymatchers.rest;

import java.util.Arrays;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Fluent request builder.
 */
public class RequestBuilder {

    private final TestRestTemplate client;
    private final UriComponentsBuilder uriBuilder;
    private final HttpMethod method;
    private final Object[] vars;
    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    private final HttpHeaders headers = new HttpHeaders();
    private Object content;

    public RequestBuilder(TestRestTemplate client, HttpMethod method, String url, Object... vars) {
        this.client = client;
        this.uriBuilder = UriComponentsBuilder.fromUriString(url);
        this.vars = vars;
        this.method = method;
    }

    /**
     * Perform a request and return a type that allows chaining further
     * actions, such as asserting expectations, on the result.
     *
     * @return response entity encapsulated as a functional action object
     */
    public ReponseActions perform() {
        var uri = uriBuilder.queryParams(params).buildAndExpand(vars).toUri();
        return new ReponseActions(client.exchange(uri, method, new HttpEntity<>(content, headers), String.class));
    }

    /**
     * Set the request body.
     */
    public RequestBuilder withContent(Object content) {
        this.content = content;
        return this;
    }

    /**
     * Set the authentication bearer header.
     */
    public RequestBuilder withUser(String bearerToken) {
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
        return this;
    }

    /**
     * Set search string parameters.
     */
    public RequestBuilder withParam(String name, String... values) {
        params.addAll(name, Arrays.asList(values));
        return this;
    }

    /**
     * Add a header.
     */
    public RequestBuilder withHeader(String name, String value) {
        headers.add(name, value);
        return this;
    }

    /**
     * Set 'Content-Type' header to a well-defined value.
     */
    public RequestBuilder contentType(MediaType contentType) {
        headers.setContentType(contentType);
        return this;
    }

    /**
     * Set the 'Content-Type' header to any string value.
     */
    public RequestBuilder contentType(String contentType) {
        headers.set(HttpHeaders.CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * Set the 'Accept' header to well-defined values.
     */
    public RequestBuilder accept(MediaType... mediaTypes) {
        headers.set(HttpHeaders.ACCEPT, MediaType.toString(Arrays.asList(mediaTypes)));
        return this;
    }

    /**
     * Set the 'Accept' header using any string values.
     */
    public RequestBuilder accept(String... mediaTypes) {
        headers.set(HttpHeaders.ACCEPT, String.join(", ", mediaTypes));
        return this;
    }
}
