package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    @Test
    void invalidRoute() {
        RequestHandler handler = new RequestHandler();

        assertNull(handler.request("invalid", null));
    }

    @Test
    void authRoute() {
        RequestHandler handler = new RequestHandler();

        assertNotNull(handler.request("auth", null));
    }

    @Test
    void characterRoute() {
        RequestHandler handler = new RequestHandler();

        assertNotNull(handler.request("character", null));
    }

    @Test
    void challengeRoute() {
        RequestHandler handler = new RequestHandler();

        assertNotNull(handler.request("challenge", null));
    }
}