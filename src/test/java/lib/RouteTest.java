package lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    @Test
    void parser() {
        Route route = new Route("example/route");
        assertEquals("example", route.pop());
    }
}