package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {
    private static AuthenticationService service = new AuthenticationService();
    private static String ID1 = "N86II";
    private static String ID2 = "N87JP";

    @BeforeAll
    static void setup() {
        service.signup("user1", "1234");
    }

    @Test
    void testSignup() {
        ResponseBody res = service.signup("user2", "4321");
        assertTrue(res.ok);
    }

    @Test
    void testFailedSignup() {
        ResponseBody res = service.signup("user1", "1234");
        assertFalse(res.ok);
    }

    @Test
    void testLogin() {
        ResponseBody res = service.login("user1", "1234");
        assertTrue(res.ok);
    }

    @Test
    void testFailedLogin() {
        ResponseBody res = service.login("user1", "0");
        assertFalse(res.ok);
    }

    @Test void testDelete() {
        ResponseBody res1 = service.delete(ID1);
        ResponseBody res2 = service.login("user1", "1234");
        assertTrue(!(res1.ok && (!res2.ok)));
    }

    @AfterAll
    static void afterAll() {
        service.delete(ID1);
        service.delete(ID2);
    }
}