package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    @Test
    void invalidRoute() {
        RequestHandler handler = new RequestHandler();

        assertNull(handler.request("/invalid", null));
    }
}