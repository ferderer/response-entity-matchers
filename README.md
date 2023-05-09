[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Build](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml/badge.svg)](https://github.com/ferderer/response-entity-matchers/actions/workflows/maven.yml)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/de.ferderer/response-entity-matchers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.ferderer/response-entity-matchers)

# ResponseEntity-Matchers
ResponseEntity-Matchers provides Hamcrest matchers to assert the response returned by the Spring's RestTemplate.

## Motivation
Spring Framework provides MockMvc to test MVC controllers, however, MockMvc only tests the request dispatcher, and sometimes we want to test the whole thing. For example, Crnk (which implements JSON:API) provides a separate servlet. MockMvc also can't test error handling in its entirety, as well as the JSP output.

As the reader probably knows, Spring Frameworks offers a beautiful way to test controllers via the MockMvc. Unfortunately, MockMvc is not the real thing, and can't help in several cases. For example, JSON:API's implementation library Crnk doesn't go through a Spring controller, and cannot be tested via the MockMvc. JSP is another case, where MockMvc is of little use. And if you wish to test all the filters, for example error handling in its entirety, you are also stuck. That was the reason to provide a library, which provides the same interface, but tests the real server. 

## Installation
The library is available via Maven Central:
```xml
  <dependency>
      <groupId>de.ferderer</groupId>
      <artifactId>response-entity-matchers</artifactId>
      <version>1.0.4</version>
      <scope>test</scope>
  </dependency>
```

## Usage
After dependency inclusion, you may write tests basically the same way you would do with MockMvc; however, there are real HTTP requests made against the real server. You may even test external servers!
```java
public class ControllerIT extends WebTestBase {

    @Test
    public void fetchAdmin() throws Exception {
      perform(get("/user/{id}", TestUsers.ADMIN_ID)
          .header(HttpHeaders.ACCEPT_LANGUAGE, Locale.GERMAN.toLanguageTag())
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestUsers.ADMIN_TOKEN))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id", is(TestUsers.ADMIN_ID)));
    }
}
```
