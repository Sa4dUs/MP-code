package server;

import org.junit.jupiter.api.Test;
import server.nosql.Document;
import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void testGetDocument() {
        Operator operator = new Operator("John", "password");
        Document document = operator.getDocument();

        assertNotNull(document);

        assertEquals("John", document.getProperty("name"));
        assertEquals(Crypto.encrypt("password"), document.getProperty("password"));
    }
}

