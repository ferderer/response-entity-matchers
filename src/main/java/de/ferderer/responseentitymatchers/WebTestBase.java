package de.ferderer.responseentitymatchers;

import de.ferderer.responseentitymatchers.rest.RequestBuilder;
import de.ferderer.responseentitymatchers.rest.ReponseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
@ContextConfiguration
public abstract class WebTestBase {

    @Value("${testing.default-base-url:http://localhost}")
    private String defaultBaseUrl;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate client;

    /**
     * Prepends the supplied url with the default hostname and port if missing. You may provide your own hostname
     * instead of localhost via property testing.default-base-url. Port number is selected randomly and will be
     * automatically inserted into the request url unless the provided URL begin with the protocol.
     *
     * Note, that you may also test external servers simply providing complete urls.
     */
    protected String fixUrl(String url) {
        return url.startsWith("http") ? url : defaultBaseUrl + ':' + port + url;
    }

    /**
     * Executes the request and returns fluent interface for assertion chaining.
     */
    public ReponseActions perform(RequestBuilder builder) throws Exception {
        return builder.perform();
    }

    /**
     * Creates {@link RequestBuilder} for a GET request.
     */
    public RequestBuilder get(String url, Object... vars) {
        return new RequestBuilder(client, HttpMethod.GET, fixUrl(url), vars);
    }

    /**
     * Creates {@link RequestBuilder} for a POST request.
     */
    public RequestBuilder post(String url, Object... vars) {
        return new RequestBuilder(client, HttpMethod.POST, fixUrl(url), vars);
    }

    /**
     * Creates {@link RequestBuilder} for a PUT request.
     */
    public RequestBuilder put(String url, Object... vars) {
        return new RequestBuilder(client, HttpMethod.PUT, fixUrl(url), vars);
    }

    /**
     * Creates {@link RequestBuilder} for a DELETE request.
     */
    public RequestBuilder delete(String url, Object... vars) {
        return new RequestBuilder(client, HttpMethod.DELETE, fixUrl(url), vars);
    }
}
