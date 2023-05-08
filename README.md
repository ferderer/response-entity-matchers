[![Java CI with Maven](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml/badge.svg)](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml)

# ResponseEntity-Matchers
ResponseEntity-Matchers provides Hamcrest matchers to assert the response returned by the Spring's RestTemplate.

## Motivation
Spring Framework provides MockMvc to test MVC controllers, however, MockMvc only tests the request dispatcher, and sometimes we want to test the whole thing. For example, Crnk (which implements JSON:API) provides a separate servlet. MockMvc also can't test error handling in its entirety, as well as the JSP output. This small library allows you to run the same tests against a real server.

## Installation
TBD

## Usage
```java
        perform(get("/user/{id}", "447ac2aa1f4111ea978f2e728ce88125")
            .header(HttpHeaders.ACCEPT_LANGUAGE, Locale.GERMAN.toLanguageTag())
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestUsers.VADIM))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("447ac2aa1f4111ea978f2e728ce88125")));
```
