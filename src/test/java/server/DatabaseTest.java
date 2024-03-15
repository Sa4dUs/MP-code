package server;

import lib.RandomGenerator;
import org.junit.jupiter.api.*;

import server.nosql.Document;
import server.nosql.Query;
import server.nosql.Schema;
import server.nosql.UserSchema;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static final String COLLECTION_NAME = "users-test";

    @BeforeAll
    static void setUp() {
        Schema userSchema = new UserSchema();
    }

    @BeforeEach
    void cleanCollection() {
        Database.deleteMany(COLLECTION_NAME, new Query());
    }

    @Test
    void insertOne() {
        Document doc = new Document(new UserSchema());
        doc.setProperty("username", "Sa4dUs");
        doc.setProperty("password", "1234");
        Database.insertOne(COLLECTION_NAME, doc);
    }

    @Test
    void insertMany() {
        int n = 10;
        List<Document> documents = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Document doc = new Document(new UserSchema());
            doc.setProperty("username", RandomGenerator.generateRandomString(10));
            doc.setProperty("password", RandomGenerator.generateRandomString(50));
            documents.add(doc);
        }

        Database.insertMany(COLLECTION_NAME, documents);
    }

    @Test
    void updateOne() {
        Document initialDoc = new Document(new UserSchema());
        initialDoc.setProperty("username", "testuser");
        initialDoc.setProperty("password", "initialpassword");
        Database.insertOne(COLLECTION_NAME, initialDoc);

        Query query = new Query();
        query.addFilter("username", "testuser");

        Document updatedDoc = new Document(new UserSchema());
        updatedDoc.setProperty("password", "newpassword");
        Database.updateOne(COLLECTION_NAME, updatedDoc, query);

        Document retrievedDoc = Database.findOne(COLLECTION_NAME, query);
        assertNotNull(retrievedDoc);
        assertEquals("testuser", retrievedDoc.getProperty("username"));
        assertEquals("newpassword", retrievedDoc.getProperty("password"));
    }

    @Test
    void findOne() {
        Document initialDoc = new Document(new UserSchema());
        initialDoc.setProperty("username", "testuser");
        initialDoc.setProperty("password", "testpassword");
        Database.insertOne(COLLECTION_NAME, initialDoc);

        Query query = new Query();
        query.addFilter("username", "testuser");

        Document foundDoc = Database.findOne(COLLECTION_NAME, query);

        assertNotNull(foundDoc);
        assertEquals("testuser", foundDoc.getProperty("username"));
        assertEquals("testpassword", foundDoc.getProperty("password"));
    }

    @Test
    void findMany() {
        Document initialDoc1 = new Document(new UserSchema());
        initialDoc1.setProperty("username", "user1");
        initialDoc1.setProperty("password", "password1");
        Database.insertOne(COLLECTION_NAME, initialDoc1);

        Document initialDoc2 = new Document(new UserSchema());
        initialDoc2.setProperty("username", "user2");
        initialDoc2.setProperty("password", "password2");
        Database.insertOne(COLLECTION_NAME, initialDoc2);

        Query query = new Query();
        query.addFilter("username", "user1");

        List<Document> foundDocs = Database.findMany(COLLECTION_NAME, query);

        assertNotNull(foundDocs);
        assertEquals(1, foundDocs.size());

        Document foundDoc = foundDocs.get(0);
        assertEquals("user1", foundDoc.getProperty("username"));
        assertEquals("password1", foundDoc.getProperty("password"));
    }

    @Test
    void deleteMany() {
        int n = 5;

        for (int i = 0; i < n; i++) {
            Document doc = new Document(new UserSchema());
            doc.setProperty("username", "user" + i);
            doc.setProperty("password", "password" + i);
            Database.insertOne(COLLECTION_NAME, doc);
        }

        Query query = new Query();
        for (int i = 0; i < n; i += 2) {
            query.addFilter("username", "user" + i);
        }

        Database.deleteMany(COLLECTION_NAME, query);

        List<Document> deletedDocs = Database.findMany(COLLECTION_NAME, query);
        assertEquals(0, deletedDocs.size());
    }

    @AfterAll
    static void tearDown() {
        Database.deleteMany(COLLECTION_NAME, new Query());
    }
}
