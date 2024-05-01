package server.nosql;

import org.junit.jupiter.api.Test;
import server.nosql.Schemas.ItemSchema;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void testAddFilter() {
        Query query = new Query();
        query.addFilter("example", 0);
        assertEquals(query.getQueryMap().get("example"), 0);
    }

    @Test
    void testMatches() {
        Document doc = new Document(new ItemSchema());
        doc.setProperty("field", "");

        Query query = new Query();
        query.addFilter("field", "");

        assertTrue(query.matches(doc));

        doc.setProperty("field", " ");
        assertFalse(query.matches(doc));
    }
}