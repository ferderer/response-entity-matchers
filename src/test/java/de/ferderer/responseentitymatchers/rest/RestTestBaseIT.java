package de.ferderer.responseentitymatchers.rest;

import static de.ferderer.responseentitymatchers.matcher.MatcherFactory.*;
import static org.hamcrest.CoreMatchers.is;
import de.ferderer.responseentitymatchers.WebTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class RestTestBaseIT extends WebTestBase {

    private static final String USER = "{\"username\":\"Jane Doe\",\"firstname\":\"Jane\",\"lastname\":\"Doe\"}";

    @Test
    public void getMethod() throws Exception {
        perform(get("/test/ok"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("John")));
    }

    @Test
    public void fluentGetMethod() throws Exception {
        get("/test/ok")
            .perform()
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("John")));
    }

    @Test
    public void postMethod() throws Exception {
        post("/test/ok")
            .withContent(USER)
            .contentType(MediaType.APPLICATION_JSON)
            .perform()
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("Jane")));
    }

    @Test
    public void putMethod() throws Exception {
        put("/test/ok")
            .withContent(USER)
            .contentType(MediaType.APPLICATION_JSON)
            .perform()
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstname", is("Jane")));
    }

    @Test
    public void delete() throws Exception {
        delete("/test/{id}", "Jane")
            .withParam("id", "Jane")
            .perform()
            .andExpect(status().isOk())
            .andExpect(content().string("Jane"));
    }
}
