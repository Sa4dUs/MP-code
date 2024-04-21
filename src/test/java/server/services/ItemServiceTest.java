package server.services;

import lib.JSON;
import lib.ResponseBody;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.ItemSchema;
import server.nosql.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {
     private static ItemService service;
     static Item item;

    @BeforeAll
    static void setUp () {
        service = new ItemService();

        item = new Item("Hello World!");
        item.getDocument().saveToDatabase(Item.class);

        new Item("¡Hola Mundo!").getDocument().saveToDatabase(Item.class);
    }

    @AfterAll
    static void tearDown() {
        List<Item> list = (List<Item>) service.getAll(Item.class).getField("data");

        for (Item item: list) {
            service.deleteItem(item);
        }
    }

    @Test
    void getItem() {
        String id = ((Item) service.getItem(item.getId(), Item.class).getField("data")).getId();
        assertEquals(id, item.getId());
    }

    @Test
    void getAll() {
        List<Item> list = (List<Item>) service.getAll(Item.class).getField("data");
        assertEquals(list.size(), 2);
    }

    @Test
    void setItem() {
        item.setField("Hola Mundo");

        service.setItem(item);

        Item updated = (Item) service.getItem(item.getId(), Item.class).getField("data");

        assertEquals(updated.getField(), "Hola Mundo");
    }

    @Test
    void deleteItem() {
        Integer length;

        {
            List<Item> list = (List<Item>) service.getAll(Item.class).getField("data");
            length = list.size();
        }

        service.deleteItem(item);

        List<Item> list = (List<Item>) service.getAll(Item.class).getField("data");
        assertEquals(list.size(), length - 1);
    }
}