[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Build](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml/badge.svg)](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.ferderer/response-entity-matchers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.ferderer/response-entity-matchers)

# response-entity-matchers

Fluent Hamcrest-style assertions for Spring's `ResponseEntity` — the same `andExpect()` experience you know from `MockMvc`, but for real HTTP requests against a real server.

## Why not MockMvc?

`MockMvc` tests the Spring dispatcher servlet in isolation. That's useful but not sufficient in several real-world cases:

- **JSON:API libraries** like Crnk bypass Spring controllers entirely
- **JSP rendering** isn't exercised by MockMvc
- **Filter chains** (e.g. error handling, security, CORS) may not be fully triggered
- **External services** can't be tested at all
- **Integration confidence** requires hitting the actual embedded server

`response-entity-matchers` fills this gap. It wraps `TestRestTemplate` with the familiar fluent assertion interface, so you can write real integration tests without learning a new API.

## Installation

```xml
<dependency>
    <groupId>de.ferderer</groupId>
    <artifactId>response-entity-matchers</artifactId>
    <version>1.1.0</version>
    <scope>test</scope>
</dependency>
```

Requires Java 17+ and Spring Boot 2.7+.

## Quick Start

Extend `WebTestBase` and write tests exactly as you would with MockMvc:

```java
import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.Matchers.*;

public class UserControllerIT extends WebTestBase {

    @Test
    void fetchAdmin() throws Exception {
        perform(get("/user/{id}", TestUsers.ADMIN_ID)
                .withHeader(HttpHeaders.ACCEPT_LANGUAGE, Locale.GERMAN.toLanguageTag())
                .withUser(TestUsers.ADMIN_TOKEN))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(TestUsers.ADMIN_ID)));
    }
}
```

`WebTestBase` starts the application on a random port and wires `TestRestTemplate` automatically. No additional configuration required.

## Request Building

All HTTP methods are available out of the box. URL variables use the same `{placeholder}` syntax as `UriComponentsBuilder`:

```java
get("/users/{id}", 42)
post("/orders")
put("/products/{id}", productId)
patch("/users/{id}/status", userId)
delete("/sessions/{token}", token)
head("/resources/{name}", name)
options("/api/endpoint")
```

### Request Configuration

```java
perform(post("/orders")
    .withUser("my-bearer-token")              // Authorization: Bearer ...
    .withHeader("X-Tenant-Id", "acme")        // arbitrary header
    .contentType(MediaType.APPLICATION_JSON)  // Content-Type
    .accept(MediaType.APPLICATION_JSON)       // Accept
    .withParam("page", "0")                   // query parameters
    .withParam("size", "20")
    .withContent(orderDto))                   // request body
```

### Testing External Servers

Any URL that begins with `http` is used as-is, bypassing the local port injection:

```java
perform(get("https://api.example.com/health"))
    .andExpect(status().isOk());
```

The local base URL can also be overridden via `testing.default-base-url` in `application-test.properties`.

## Assertions

All assertions are accessed through static factory methods in `MatcherFactory`. Use a static import for clean, readable tests:

```java
import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
```

### Status

```java
.andExpect(status().isOk())                  // 200
.andExpect(status().isCreated())             // 201
.andExpect(status().isNoContent())           // 204
.andExpect(status().isBadRequest())          // 400
.andExpect(status().isUnauthorized())        // 401
.andExpect(status().isForbidden())           // 403
.andExpect(status().isNotFound())            // 404
.andExpect(status().isInternalServerError()) // 500

// Range checks
.andExpect(status().is2xxSuccessful())
.andExpect(status().is4xxClientError())
.andExpect(status().is5xxServerError())

// Exact numeric value
.andExpect(status().is(201))

// Hamcrest matcher
.andExpect(status().is(equalTo(HttpStatus.OK)))
```

Every standard HTTP status code has a dedicated named method. See `StatusMatchers` for the full list.

### Content Type

```java
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(content().contentType("application/json;charset=UTF-8"))

// Compatibility check (ignores parameters like charset)
.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
```

### Response Body

```java
// Exact string match
.andExpect(content().string("pong"))

// Hamcrest matcher on the body string
.andExpect(content().string(containsString("admin")))
.andExpect(content().string(not(emptyOrNullString())))

// JSON structural equality (lenient: extensible, non-strict array order)
.andExpect(content().json("{\"status\": \"ok\"}"))

// JSON strict equality
.andExpect(content().json("{\"status\": \"ok\"}", true))

// XML structural equality
.andExpect(content().xml("<result><status>ok</status></result>"))

// XML via Hamcrest node matcher
.andExpect(content().node(hasXPath("//status[text()='ok']")))
```

### JSON Path

```java
// Value equality
.andExpect(jsonPath("$.id", is(42)))
.andExpect(jsonPath("$.name", is("Alice")))
.andExpect(jsonPath("$.active", is(true)))

// Hamcrest matchers
.andExpect(jsonPath("$.email", containsString("@")))
.andExpect(jsonPath("$.score", greaterThan(0.0), Double.class))

// Existence
.andExpect(jsonPath("$.id").exists())
.andExpect(jsonPath("$.deletedAt").doesNotExist())

// Type assertions
.andExpect(jsonPath("$.id").isNumber())
.andExpect(jsonPath("$.name").isString())
.andExpect(jsonPath("$.enabled").isBoolean())
.andExpect(jsonPath("$.tags").isArray())
.andExpect(jsonPath("$.metadata").isMap())

// Emptiness
.andExpect(jsonPath("$.tags").isEmpty())
.andExpect(jsonPath("$.items").isNotEmpty())

// Parameterized path expressions
.andExpect(jsonPath("$.roles[%d].name", 0).value("ADMIN"))
```

### Headers

```java
// Exact value
.andExpect(header().string("X-Request-Id", "abc-123"))

// Hamcrest matcher
.andExpect(header().string("Location", containsString("/orders/")))

// Multiple values
.andExpect(header().stringValues("Cache-Control", "no-cache", "no-store"))

// Existence
.andExpect(header().exists("X-Correlation-Id"))
.andExpect(header().doesNotExist("X-Internal-Secret"))

// Numeric
.andExpect(header().longValue("Content-Length", 512L))

// Date
.andExpect(header().dateValue("Last-Modified", lastModifiedMillis))
```

## Response Handlers

Handlers are invoked with `andDo()` and do not affect assertion chaining:

```java
import static de.ferderer.responseentitymatchers.handler.HandlerFactory.*;

perform(get("/debug/info"))
    .andDo(print())              // prints status, headers, body to stdout
    .andExpect(status().isOk());

// Print to a specific stream or writer
.andDo(print(myWriter))
```

This is useful during development to inspect a full response without interrupting the assertion chain.

## Accessing the Raw Response

When you need the `ResponseEntity` for manual inspection:

```java
ResponseEntity<?> response = perform(get("/users/1"))
    .andExpect(status().isOk())
    .andReturn();

String body = (String) response.getBody();
String etag = response.getHeaders().getETag();
```

## Custom Matchers

`ResponseMatcher` is a `@FunctionalInterface` — custom assertions are a single lambda:

```java
ResponseMatcher hasNonEmptyBody = result ->
    assertNotNull("Body must not be null", result.getBody());

ResponseMatcher hasValidUuid = result ->
    assertTrue("Location must contain UUID",
        result.getHeaders().getLocation().toString().matches(".*/[0-9a-f-]{36}"));

perform(post("/users").withContent(newUser))
    .andExpect(status().isCreated())
    .andExpect(hasValidUuid)
    .andExpect(hasNonEmptyBody);
```

## Full Example

```java
import static de.ferderer.responseentitymatchers.handler.HandlerFactory.*;
import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.Matchers.*;

class OrderControllerIT extends WebTestBase {

    @Test
    void createOrder_returnsCreatedWithLocation() throws Exception {
        var payload = """
            {"productId": 7, "quantity": 3}
            """;

        perform(post("/orders")
                .withUser(TestUsers.CUSTOMER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .withContent(payload))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.status", is("PENDING")))
            .andExpect(jsonPath("$.items").isNotEmpty());
    }

    @Test
    void getOrder_notFound_returns404WithProblemDetail() throws Exception {
        perform(get("/orders/{id}", 9999)
                .withUser(TestUsers.CUSTOMER_TOKEN)
                .accept(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title", is("Not Found")))
            .andExpect(jsonPath("$.detail", containsString("9999")));
    }
}
```

## Configuration Reference

| Property | Default | Description |
|---|---|---|
| `testing.default-base-url` | `http://localhost` | Base URL prepended to relative request paths |

The server port is injected automatically via `@LocalServerPort` and appended unless the URL begins with `http`.

## License

Apache License 2.0 — see [LICENSE](LICENSE) for details.
