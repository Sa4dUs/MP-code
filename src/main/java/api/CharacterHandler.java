package api;

import lib.NFunction;
import lib.RequestBody;
import lib.ResponseBody;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.services.AuthenticationService;
import server.services.CharacterService;

public class CharacterHandler extends Handler {
    private CharacterService service = null;

    public CharacterHandler() {
        this.service = new CharacterService();
        this.operations.put(null, req -> new ResponseBody());
        this.operations.put("get", req -> this.service.getCharacterFromPlayerNick((String) req.getField("nick")));
        this.operations.put("createCharacter", req -> this.service.createCharacter((Character) req.getField("character")));
        this.operations.put("createPlayerCharacter", req -> this.service.createCharacter((PlayerCharacter) req.getField("character")));
        this.operations.put("updateCharacter", req -> this.service.updateCharacter((Character) req.getField("character")));
        this.operations.put("updatePlayerCharacter", req -> this.service.updatePlayerCharacter((PlayerCharacter) req.getField("character")));
        this.operations.put("delete", req -> this.service.deleteCharacter((String) req.getField("id"), (Class<?>) req.getField("clazz")));
        this.operations.put("default", req -> this.service.getDefaultCharacters());
        this.operations.put("player", req -> this.service.getPlayerCharacters());
        this.operations.put("setCharacterOfPlayer", req -> this.service.setCharacterOfPlayer((String) req.getField("nick"), (PlayerCharacter) req.getField("character")));
    }
}