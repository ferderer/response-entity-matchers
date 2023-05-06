package de.fv.responseentitymatchers.matcher;

import static org.junit.jupiter.api.Assertions.fail;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.core.Conventions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class StatusMatchersTest {

    private static final Set<String> DEPRECATED_STATUS_VALUES = new HashSet<>(Arrays.asList(
        "CHECKPOINT", "MOVED_TEMPORARILY", "USE_PROXY", "METHOD_FAILURE", "REQUEST_ENTITY_TOO_LARGE",
        "REQUEST_URI_TOO_LONG", "INSUFFICIENT_SPACE_ON_RESOURCE", "DESTINATION_LOCKED"));

    @Test
    public void testAll() throws Exception {
        StatusMatchers matchers = MatcherFactory.status();

        for(HttpStatus status : HttpStatus.values()) {
            ResponseEntity<?> re = ResponseEntity.status(status).build();

            // assert by value
            matchers.is(status.value()).match(re);

            // assert by hamcrest matcher
            matchers.is(CoreMatchers.equalTo(status)).match(re);

            // assert by name
            String name = status.name();
            if (!DEPRECATED_STATUS_VALUES.contains(name)) {
                ((ResponseMatcher) ReflectionUtils.invokeMethod(getMethodForHttpStatus(name), matchers)).match(re);
            }

            // assert by range
            switch(status.series().value()) {
                case 1 -> matchers.is1xxInformational().match(re);
                case 2 -> matchers.is2xxSuccessful().match(re);
                case 3 -> matchers.is3xxRedirection().match(re);
                case 4 -> matchers.is4xxClientError().match(re);
                case 5 -> matchers.is5xxServerError().match(re);
                default -> fail("Unexpected range for status code value " + status);
            }
        }
    }

    private Method getMethodForHttpStatus(String status) throws NoSuchMethodException {
        String name = status.toLowerCase().replace("_", "-");
        name = "is" + StringUtils.capitalize(Conventions.attributeNameToPropertyName(name));
        return StatusMatchers.class.getMethod(name);
    }
}
