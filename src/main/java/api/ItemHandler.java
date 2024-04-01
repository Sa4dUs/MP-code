package api;

import server.nosql.JSONable;
import server.services.ItemService;

public class ItemHandler extends Handler{

    private ItemService service = null;

    public ItemHandler()
    {
        this.service = new ItemService();
        this.operations.put("get", req -> this.service.getItem((String) req.getField("id"), (Class<?>) req.getField("clazz")));
        this.operations.put("getAll", req -> this.service.getAll((Class<?>) req.getField("clazz")));
        this.operations.put("set", req -> this.service.setItem((JSONable) req.getField("object")));
    }
}
