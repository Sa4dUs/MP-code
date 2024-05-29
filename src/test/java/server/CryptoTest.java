package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {

    String key = "Jorge usó explosión!";

    @Test
    void testUUIDv4() {
        assertNotEquals(Crypto.UUIDv4(), "");
    }

    @Test
    void testEncryptDecrypt() {
        String encrypted = Crypto.encrypt(key);
        assertNotNull(encrypted);

        String decrypted = Crypto.decrypt(encrypted);
        assertNotNull(decrypted);
        assertEquals(key, decrypted);
    }

    @Test
    void testCompare() {
        String encrypted = Crypto.encrypt(key);

        assertTrue(Crypto.compare(encrypted, key));
        assertFalse(Crypto.compare(encrypted, key + "a"));
    @Test
    void UUIDv4() {
    }

    @Test
    void encrypt() {
    }

    @Test
    void decrypt() {
    }

    @Test
    void compare() {
    }
}