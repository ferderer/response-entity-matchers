package de.ferderer.responseentitymatchers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApp {

    public static void main(String... args) {
        SpringApplication.run(TestApp.class, args);
    }

    public static record Person(String username, String firstname, String lastname) {}

    @GetMapping("/test/ok")
    public Person testGetMethod() {
        return new Person("John Doe", "John", "Doe");
    }

    @PostMapping("/test/ok")
    public Person testPostMethod(@RequestBody Person person) {
        return person;
    }

    @PutMapping("/test/ok")
    public Person testPutMethod(@RequestBody Person person) {
        return person;
    }

    @PatchMapping("/test/ok")
    public Person testPatchMethod(@RequestBody Person person) {
        return person;
    }

    @DeleteMapping("/test/{id}")
    public String testDeleteMethod(@PathVariable("id") String id) {
        return id;
    }
}
