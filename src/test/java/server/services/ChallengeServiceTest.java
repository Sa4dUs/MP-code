package server.services;

import lib.ResponseBody;
import org.junit.jupiter.api.Test;
import server.Database;
import server.Player;
import server.User;
import server.characters.Character;
import server.characters.CharacterType;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Demon;
import server.minions.Human;
import server.nosql.Document;
import server.nosql.Query;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeServiceTest {

    @Test
    public void saveCharacter()
    {
        Weapon weapon = new Weapon();
        weapon.setName("Excalibur");
        weapon.setAttack(2);
        weapon.setDefense(0);
        weapon.setTwoHanded(false);

        Weapon weapon1 = new Weapon();
        weapon.setName("Tu madre");
        weapon.setAttack(3);
        weapon.setDefense(3);
        weapon.setTwoHanded(true);

        Armor armor = new Armor();
        armor.setAttack(0);
        armor.setDefense(3);
        armor.setName("Pechera de espinas");

        Demon demon = new Demon();
        demon.setName("Marxelo");
        demon.setHealth(2);
        demon.setPact("Chupa chupa");

        Demon demon1 = new Demon();
        demon1.setName("Marxelo malvado");
        demon1.setHealth(3);
        demon1.setPact("Chupa chupa chupa chupa");

        demon.addMinion(demon1);

        Human human = new Human();
        human.setName("Shania");
        human.setLoyalty(0);
        human.setHealth(1);

        demon1.addMinion(human);

        Character c1 = new Character();
        c1.setName("Franco");
        c1.setBreed(CharacterType.Hunter);
        c1.addWeaponToList(weapon);
        c1.addWeaponToList(weapon1);
        c1.addMinion(demon);
        c1.addArmorToList(armor);
        Document doc = c1.getDocument();
        doc.saveToDatabase(Character.class);
    }

    @Test
    public void getCharacterTest()
    {
        Query query = new Query();
        query.addFilter("name", "Franco");
        Document doc = Database.findOne(Character.class.getName(), query);
        Character character = (Character) Document.deJSONDocument(doc, Character.class);
    }

}