package server.services;

import lib.ResponseBody;
import server.characters.Character;

public class CharacterService implements Service {
    public ResponseBody createCharacter(Character character) {
        return new ResponseBody(true);
    };

    public ResponseBody updateCharacter(String id, Character character) {
        return new ResponseBody(true);
    };

    public ResponseBody deleteCharacter(String id) {
        return new ResponseBody(true);
    };
}
