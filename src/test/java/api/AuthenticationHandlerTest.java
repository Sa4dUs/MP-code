package api;

import lib.RequestBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationHandlerTest {
    @Test
    void login() {
        RequestHandler handler = new RequestHandler();

        RequestBody data = new RequestBody();
        data.addField("username", "user");
        data.addField("password", "1234");

        assertNotNull(handler.request("auth/login", data));
    }

    @Test
    void signup() {

    }
}