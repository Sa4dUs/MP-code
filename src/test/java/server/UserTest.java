package server;

import org.junit.jupiter.api.Test;
import server.nosql.Document;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetName() {
        User user = new User("John", "password");
        assertEquals("John", user.getName());
    }

    @Test
    void testSetName() {
        User user = new User();
        user.setName("Alice");
        assertEquals("Alice", user.getName());
    }

    @Test
    void testGetNick() {
        User user = new User("John", "password");
        assertNotNull(user.getNick());
    }

    @Test
    void testGetId() {
        User user = new User("John", "password");
        assertNotNull(user.getId());
        user.setId("123456");
        assertEquals("123456", user.getId());
    }

    @Test
    void testGetDocument() {
        User user = new User("John", "password");
        Document document = user.getDocument();

        assertNotNull(document);
        assertEquals("John", document.getProperty("name"));
        assertNotNull(document.getProperty("nick"));
        assertEquals(Crypto.encrypt("password"), document.getProperty("password"));
    }
}
