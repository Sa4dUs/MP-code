package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    @BeforeAll
    static void setup() {
        AuthenticationService service = new AuthenticationService();
        service.signup("user1", "1234");
    }

    @Test
    void signup() {
        AuthenticationService service = new AuthenticationService();
        ResponseBody res = service.signup("user2", "4321");
        assertTrue(res.ok);
    }

    @Test
    void login() {
        AuthenticationService service = new AuthenticationService();
        ResponseBody res = service.login("user1", "1234");
        assertTrue(res.ok);
    }

    @Test
    void failedLogin() {
        AuthenticationService service = new AuthenticationService();
        ResponseBody res = service.login("user1", "0");
        assertFalse(res.ok);
    }
}