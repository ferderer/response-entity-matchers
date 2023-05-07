package de.ferderer.responseentitymatchers.matcher;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;

public class StatusMatchers {

    /**
     * Assert the response status code with the given Hamcrest {@link Matcher}.
     */
    public ResponseMatcher is(Matcher<HttpStatus> matcher) {
        return result -> assertTrue("Response status", matcher.matches(result.getStatusCode()));
    }

    /**
     * Assert the response status code is equal to an integer value.
     */
    public ResponseMatcher is(int status) {
        return result -> assertEquals("Response status", status, result.getStatusCode().value());
    }

    /**
     * Assert the response status code is in the 1xx range.
     */
    public ResponseMatcher is1xxInformational() {
        return result -> assertTrue("Range for response status value " + result.getStatusCode(),
            result.getStatusCode().is1xxInformational());
    }

    /**
     * Assert the response status code is in the 2xx range.
     */
    public ResponseMatcher is2xxSuccessful() {
        return result -> assertTrue("Range for response status value " + result.getStatusCode(),
            result.getStatusCode().is2xxSuccessful());
    }

    /**
     * Assert the response status code is in the 3xx range.
     */
    public ResponseMatcher is3xxRedirection() {
        return result -> assertTrue("Range for response status value " + result.getStatusCode(),
            result.getStatusCode().is3xxRedirection());
    }

    /**
     * Assert the response status code is in the 4xx range.
     */
    public ResponseMatcher is4xxClientError() {
        return result -> assertTrue("Range for response status value " + result.getStatusCode(),
            result.getStatusCode().is4xxClientError());
    }

    /**
     * Assert the response status code is in the 5xx range.
     */
    public ResponseMatcher is5xxServerError() {
        return result -> assertTrue("Range for response status value " + result.getStatusCode(),
            result.getStatusCode().is5xxServerError());
    }

    /**
     * Assert the response status code is {@code HttpStatus.CONTINUE} (100).
     */
    public ResponseMatcher isContinue() {
        return matcher(HttpStatus.CONTINUE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.SWITCHING_PROTOCOLS} (101).
     */
    public ResponseMatcher isSwitchingProtocols() {
        return matcher(HttpStatus.SWITCHING_PROTOCOLS);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PROCESSING} (102).
     */
    public ResponseMatcher isProcessing() {
        return matcher(HttpStatus.PROCESSING);
    }

    /**
     * Assert the response status code is {@code HttpStatus.EARLY_HINTS} (103).
     */
    public ResponseMatcher isEarlyHints() {
        return matcher(HttpStatus.EARLY_HINTS);
    }

    /**
     * Assert the response status code is {@code HttpStatus.CHECKPOINT} (103).
     */
    public ResponseMatcher isCheckpoint() {
        return matcher(HttpStatus.valueOf(103));
    }

    /**
     * Assert the response status code is {@code HttpStatus.OK} (200).
     */
    public ResponseMatcher isOk() {
        return matcher(HttpStatus.OK);
    }

    /**
     * Assert the response status code is {@code HttpStatus.CREATED} (201).
     */
    public ResponseMatcher isCreated() {
        return matcher(HttpStatus.CREATED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.ACCEPTED} (202).
     */
    public ResponseMatcher isAccepted() {
        return matcher(HttpStatus.ACCEPTED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NON_AUTHORITATIVE_INFORMATION} (203).
     */
    public ResponseMatcher isNonAuthoritativeInformation() {
        return matcher(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NO_CONTENT} (204).
     */
    public ResponseMatcher isNoContent() {
        return matcher(HttpStatus.NO_CONTENT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.RESET_CONTENT} (205).
     */
    public ResponseMatcher isResetContent() {
        return matcher(HttpStatus.RESET_CONTENT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PARTIAL_CONTENT} (206).
     */
    public ResponseMatcher isPartialContent() {
        return matcher(HttpStatus.PARTIAL_CONTENT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.MULTI_STATUS} (207).
     */
    public ResponseMatcher isMultiStatus() {
        return matcher(HttpStatus.MULTI_STATUS);
    }

    /**
     * Assert the response status code is {@code HttpStatus.ALREADY_REPORTED} (208).
     */
    public ResponseMatcher isAlreadyReported() {
        return matcher(HttpStatus.ALREADY_REPORTED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.IM_USED} (226).
     */
    public ResponseMatcher isImUsed() {
        return matcher(HttpStatus.IM_USED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.MULTIPLE_CHOICES} (300).
     */
    public ResponseMatcher isMultipleChoices() {
        return matcher(HttpStatus.MULTIPLE_CHOICES);
    }

    /**
     * Assert the response status code is {@code HttpStatus.MOVED_PERMANENTLY} (301).
     */
    public ResponseMatcher isMovedPermanently() {
        return matcher(HttpStatus.MOVED_PERMANENTLY);
    }

    /**
     * Assert the response status code is {@code HttpStatus.FOUND} (302).
     */
    public ResponseMatcher isFound() {
        return matcher(HttpStatus.FOUND);
    }

    /**
     * Assert the response status code is {@code HttpStatus.SEE_OTHER} (303).
     */
    public ResponseMatcher isSeeOther() {
        return matcher(HttpStatus.SEE_OTHER);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NOT_MODIFIED} (304).
     */
    public ResponseMatcher isNotModified() {
        return matcher(HttpStatus.NOT_MODIFIED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.TEMPORARY_REDIRECT} (307).
     */
    public ResponseMatcher isTemporaryRedirect() {
        return matcher(HttpStatus.TEMPORARY_REDIRECT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PERMANENT_REDIRECT} (308).
     */
    public ResponseMatcher isPermanentRedirect() {
        return matcher(HttpStatus.valueOf(308));
    }

    /**
     * Assert the response status code is {@code HttpStatus.BAD_REQUEST} (400).
     */
    public ResponseMatcher isBadRequest() {
        return matcher(HttpStatus.BAD_REQUEST);
    }

    /**
     * Assert the response status code is {@code HttpStatus.UNAUTHORIZED} (401).
     */
    public ResponseMatcher isUnauthorized() {
        return matcher(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PAYMENT_REQUIRED} (402).
     */
    public ResponseMatcher isPaymentRequired() {
        return matcher(HttpStatus.PAYMENT_REQUIRED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.FORBIDDEN} (403).
     */
    public ResponseMatcher isForbidden() {
        return matcher(HttpStatus.FORBIDDEN);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NOT_FOUND} (404).
     */
    public ResponseMatcher isNotFound() {
        return matcher(HttpStatus.NOT_FOUND);
    }

    /**
     * Assert the response status code is {@code HttpStatus.METHOD_NOT_ALLOWED} (405).
     */
    public ResponseMatcher isMethodNotAllowed() {
        return matcher(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NOT_ACCEPTABLE} (406).
     */
    public ResponseMatcher isNotAcceptable() {
        return matcher(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PROXY_AUTHENTICATION_REQUIRED} (407).
     */
    public ResponseMatcher isProxyAuthenticationRequired() {
        return matcher(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.REQUEST_TIMEOUT} (408).
     */
    public ResponseMatcher isRequestTimeout() {
        return matcher(HttpStatus.REQUEST_TIMEOUT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.CONFLICT} (409).
     */
    public ResponseMatcher isConflict() {
        return matcher(HttpStatus.CONFLICT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.GONE} (410).
     */
    public ResponseMatcher isGone() {
        return matcher(HttpStatus.GONE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.LENGTH_REQUIRED} (411).
     */
    public ResponseMatcher isLengthRequired() {
        return matcher(HttpStatus.LENGTH_REQUIRED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PRECONDITION_FAILED} (412).
     */
    public ResponseMatcher isPreconditionFailed() {
        return matcher(HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PAYLOAD_TOO_LARGE} (413).
     */
    public ResponseMatcher isPayloadTooLarge() {
        return matcher(HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.REQUEST_URI_TOO_LONG} (414).
     */
    public ResponseMatcher isUriTooLong() {
        return matcher(HttpStatus.URI_TOO_LONG);
    }

    /**
     * Assert the response status code is {@code HttpStatus.UNSUPPORTED_MEDIA_TYPE} (415).
     */
    public ResponseMatcher isUnsupportedMediaType() {
        return matcher(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE} (416).
     */
    public ResponseMatcher isRequestedRangeNotSatisfiable() {
        return matcher(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.EXPECTATION_FAILED} (417).
     */
    public ResponseMatcher isExpectationFailed() {
        return matcher(HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.I_AM_A_TEAPOT} (418).
     */
    public ResponseMatcher isIAmATeapot() {
        return matcher(HttpStatus.valueOf(418));
    }

    /**
     * Assert the response status code is {@code HttpStatus.UNPROCESSABLE_ENTITY} (422).
     */
    public ResponseMatcher isUnprocessableEntity() {
        return matcher(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Assert the response status code is {@code HttpStatus.LOCKED} (423).
     */
    public ResponseMatcher isLocked() {
        return matcher(HttpStatus.LOCKED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.FAILED_DEPENDENCY} (424).
     */
    public ResponseMatcher isFailedDependency() {
        return matcher(HttpStatus.FAILED_DEPENDENCY);
    }

    /**
     * Assert the response status code is {@code HttpStatus.TOO_EARLY} (425).
     */
    public ResponseMatcher isTooEarly() {
        return matcher(HttpStatus.valueOf(425));
    }

    /**
     * Assert the response status code is {@code HttpStatus.UPGRADE_REQUIRED} (426).
     */
    public ResponseMatcher isUpgradeRequired() {
        return matcher(HttpStatus.UPGRADE_REQUIRED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.PRECONDITION_REQUIRED} (428).
     */
    public ResponseMatcher isPreconditionRequired() {
        return matcher(HttpStatus.valueOf(428));
    }

    /**
     * Assert the response status code is {@code HttpStatus.TOO_MANY_REQUESTS} (429).
     */
    public ResponseMatcher isTooManyRequests() {
        return matcher(HttpStatus.valueOf(429));
    }

    /**
     * Assert the response status code is {@code HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE} (431).
     */
    public ResponseMatcher isRequestHeaderFieldsTooLarge() {
        return matcher(HttpStatus.valueOf(431));
    }

    /**
     * Assert the response status code is {@code HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS} (451).
     */
    public ResponseMatcher isUnavailableForLegalReasons() {
        return matcher(HttpStatus.valueOf(451));
    }

    /**
     * Assert the response status code is {@code HttpStatus.INTERNAL_SERVER_ERROR} (500).
     */
    public ResponseMatcher isInternalServerError() {
        return matcher(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NOT_IMPLEMENTED} (501).
     */
    public ResponseMatcher isNotImplemented() {
        return matcher(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.BAD_GATEWAY} (502).
     */
    public ResponseMatcher isBadGateway() {
        return matcher(HttpStatus.BAD_GATEWAY);
    }

    /**
     * Assert the response status code is {@code HttpStatus.SERVICE_UNAVAILABLE} (503).
     */
    public ResponseMatcher isServiceUnavailable() {
        return matcher(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.GATEWAY_TIMEOUT} (504).
     */
    public ResponseMatcher isGatewayTimeout() {
        return matcher(HttpStatus.GATEWAY_TIMEOUT);
    }

    /**
     * Assert the response status code is {@code HttpStatus.HTTP_VERSION_NOT_SUPPORTED} (505).
     */
    public ResponseMatcher isHttpVersionNotSupported() {
        return matcher(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.VARIANT_ALSO_NEGOTIATES} (506).
     */
    public ResponseMatcher isVariantAlsoNegotiates() {
        return matcher(HttpStatus.VARIANT_ALSO_NEGOTIATES);
    }

    /**
     * Assert the response status code is {@code HttpStatus.INSUFFICIENT_STORAGE} (507).
     */
    public ResponseMatcher isInsufficientStorage() {
        return matcher(HttpStatus.INSUFFICIENT_STORAGE);
    }

    /**
     * Assert the response status code is {@code HttpStatus.LOOP_DETECTED} (508).
     */
    public ResponseMatcher isLoopDetected() {
        return matcher(HttpStatus.LOOP_DETECTED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.BANDWIDTH_LIMIT_EXCEEDED} (509).
     */
    public ResponseMatcher isBandwidthLimitExceeded() {
        return matcher(HttpStatus.valueOf(509));
    }

    /**
     * Assert the response status code is {@code HttpStatus.NOT_EXTENDED} (510).
     */
    public ResponseMatcher isNotExtended() {
        return matcher(HttpStatus.NOT_EXTENDED);
    }

    /**
     * Assert the response status code is {@code HttpStatus.NETWORK_AUTHENTICATION_REQUIRED} (511).
     */
    public ResponseMatcher isNetworkAuthenticationRequired() {
        return matcher(HttpStatus.valueOf(511));
    }

    /**
     * Match the expected response status to that of the HttpServletResponse.
     */
    private ResponseMatcher matcher(HttpStatus status) {
        return result -> assertEquals("Status", status, result.getStatusCode());
    }

    StatusMatchers() {}
}
