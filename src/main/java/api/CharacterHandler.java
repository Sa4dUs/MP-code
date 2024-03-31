package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.characters.Character;
import server.services.AuthenticationService;
import server.services.CharacterService;

public class CharacterHandler extends Handler {
    private CharacterService service = null;

    public CharacterHandler() {
        this.service = new CharacterService();
        this.operations.put(null, req -> new ResponseBody());
        this.operations.put("create", req -> this.service.createCharacter((Character) req.getField("character")));
        this.operations.put("update", req -> this.service.updateCharacter((Character) req.getField("character")));
        this.operations.put("delete", req -> this.service.deleteCharacter((String) req.getField("id"), (Class<?>) req.getField("clazz")));
        this.operations.put("default", req -> this.service.getDefaultCharacters());
    }
}