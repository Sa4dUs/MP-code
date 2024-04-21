package server;

import org.junit.jupiter.api.Test;
import server.nosql.Document;
import static org.junit.jupiter.api.Assertions.*;

class CharacteristicTest {

    @Test
    void testSetValue() {
        Characteristic characteristic = new Resistance();
        characteristic.setValue(5); // Valor mayor que el máximo
        assertEquals(3, characteristic.getValue()); // Debería establecerse en el valor máximo
    }

    @Test
    void testToString() {
        Characteristic characteristic = new Weakness();
        characteristic.setName("Fire");
        characteristic.setValue(2);
        assertEquals("Fire Impact:2", characteristic.toString());
    }

    @Test
    void testGetDocument() {
        Characteristic characteristic = new Weakness();
        characteristic.setName("Ice");
        characteristic.setValue(1);
        Document document = characteristic.getDocument();

        assertNotNull(document);
        assertEquals("Ice", document.getProperty("name"));
        assertEquals(1, document.getProperty("value"));
    }

    // Prueba de métodos getters y setters
    @Test
    void testGetAndSetName() {
        Characteristic characteristic = new Weakness();
        characteristic.setName("Wind");
        assertEquals("Wind", characteristic.getName());
    }

    @Test
    void testGetAndSetId() {
        Characteristic characteristic = new Weakness();
        characteristic.setId("123456");
        assertEquals("123456", characteristic.getId());
    }
}
