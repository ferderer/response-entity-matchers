package de.ferderer.responseentitymatchers.handler;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import org.springframework.http.ResponseEntity;

/**
 * Static factory methods for creating response handlers.
 *
 * @author Vadim Ferderer
 * @since 1.0
 */
public class HandlerFactory {

    /**
     * Print {@link ResponseEntity} details to the "standard" output stream.
     */
    public static ResponseHandler print() {
        return print(System.out);
    }

    /**
     * Print {@link ResponseEntity} details to the supplied {@link OutputStream}.
     */
    public static ResponseHandler print(OutputStream stream) {
        return new Printer(new PrintWriter(stream, true));
    }

    /**
     * Print {@link ResponseEntity} details to the supplied {@link Writer}.
     */
    public static ResponseHandler print(Writer writer) {
        return new Printer(new PrintWriter(writer, true));
    }

    /**
     * Printer class that writes to a {@link PrintWriter}.
     */
    private static class Printer implements ResponseHandler {

        private final PrintWriter printWriter;

        Printer(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        public void handle(ResponseEntity<?> result) throws Exception {
            printWriter.println("Status: " + result.getStatusCode());
            result.getHeaders().forEach((name, values) ->
                values.forEach(value -> printWriter.println(name + ": " + value)));
            printWriter.println();
            printWriter.println(result.getBody());
        }
    }

    private HandlerFactory() {}
}
