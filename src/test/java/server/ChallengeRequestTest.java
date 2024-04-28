package server;

import org.junit.jupiter.api.Test;
import server.nosql.Document;
import static org.junit.jupiter.api.Assertions.*;

class ChallengeRequestTest {

    @Test
    void testGettersAndSetters() {
        ChallengeRequest request = new ChallengeRequest();
        request.setAttackerId("attacker123");
        request.setAttackedId("attacked456");
        request.setBet(50);

        assertEquals("attacker123", request.getAttackerId());
        assertEquals("attacked456", request.getAttackedId());
        assertEquals(50, request.getBet());
    }

    @Test
    void testGetDocument() {
        ChallengeRequest request = new ChallengeRequest("attacker123", "attacked456", 50);
        Document document = request.getDocument();

        assertNotNull(document);
        assertEquals(50, document.getProperty("bet"));
        assertEquals("attacker123", document.getProperty("attackerId"));
        assertEquals("attacked456", document.getProperty("attackedId"));
    }

    @Test
    void testIdGeneration() {
        ChallengeRequest request = new ChallengeRequest("attacker123", "attacked456", 50);

        Document document = request.getDocument();
        assertNotNull(request.getId()); // El ID debería ser generado después de generar el documento
    }

    @Test
    void testSettersWithNull() {
        ChallengeRequest request = new ChallengeRequest("attacker123", "attacked456", 50);
        // Intentar establecer valores nulos para los identificadores
        assertThrows(IllegalArgumentException.class, () -> request.setAttackerId(null));
        assertThrows(IllegalArgumentException.class, () -> request.setAttackedId(null));
    }

    @Test
    void testNegativeBet() {
        ChallengeRequest request = new ChallengeRequest();
        // Intentar establecer una apuesta negativa
        assertThrows(IllegalArgumentException.class, () -> request.setBet(-10));
    }
}
