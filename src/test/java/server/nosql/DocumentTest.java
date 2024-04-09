package server.nosql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Characteristic;
import server.Database;
import server.Resistance;
import server.Weakness;
import server.items.*;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @BeforeAll
    public static void cleanUp() {
        Database.deleteMany(Weapon.class.getName(), new Query());
        Database.deleteMany(Armor.class.getName(), new Query());
        Database.deleteMany(Ability.class.getName(), new Query());
        Database.deleteMany(Characteristic.class.getName(), new Query());
        Database.deleteMany(Human.class.getName(), new Query());
        Database.deleteMany(Demon.class.getName(), new Query());
        Database.deleteMany(Ghoul.class.getName(), new Query());
    }
    @Test
    public void createItems()
    {
        Weapon weapon = new Weapon();
        weapon.setTwoHanded(false);
        weapon.setAttack(2);
        weapon.setDefense(1);
        weapon.setName("Daga");

        Weapon weapon1 = new Weapon();
        weapon1.setTwoHanded(false);
        weapon1.setAttack(3);
        weapon1.setDefense(0);
        weapon1.setName("Daga de cristal");

        Weapon weapon2 = new Weapon();
        weapon2.setTwoHanded(true);
        weapon2.setAttack(3);
        weapon2.setDefense(3);
        weapon2.setName("Mandoble");

        Armor armor = new Armor();
        armor.setName("Malla de espinas");
        armor.setDefense(1);
        armor.setAttack(2);

        Armor armor1 = new Armor();
        armor1.setName("Coraza impenetrable");
        armor1.setDefense(3);
        armor1.setAttack(0);

        Ability ability = new Talent();
        ability.setName("Bola de fuego");
        ability.setAttack(3);
        ability.setDefense(0);
        ability.setCost(2);

        Ability ability1 = new Blessing();
        ability1.setName("Pared de hielo");
        ability1.setAttack(0);
        ability1.setDefense(3);
        ability1.setCost(3);

        Ability ability2 = new Talent();
        ability2.setName("Talento de cazador");
        ability2.setAttack(3);
        ability2.setDefense(0);
        ability2.setCost(1);

        Characteristic characteristic = new Weakness();
        characteristic.setValue(3);
        characteristic.setName("Debilidad al sol");

        Characteristic characteristic1 = new Resistance();
        characteristic1.setValue(3);
        characteristic1.setName("Resistencia al fuego");

        Human human = new Human();
        human.setName("Judas");
        human.setLoyalty(1);
        human.setHealth(2);

        Ghoul ghoul = new Ghoul();
        ghoul.setName("Kaneki");
        ghoul.setHealth(2);
        ghoul.setDependence(3);

        Demon demon = new Demon();
        demon.setPact("Contrato de alma");
        demon.setHealth(3);
        demon.setName("Satan");

        Demon demon1 = new Demon();
        demon1.setPact("Amor eterno");
        demon1.setHealth(3);
        demon1.setName("Introducir nombre de demonio que tenga algo que ver con amor");
        demon1.addMinion(human);

        Demon demon2 = new Demon();
        demon2.setPact("Riquezas infinitas");
        demon2.setHealth(3);
        demon2.setName("Mammon");

        Demon demon3 = new Demon();
        demon3.setPact("Sabiduría suprema");
        demon3.setHealth(3);
        demon3.setName("Thoth");

// Creación de más ghouls
        Ghoul ghoul2 = new Ghoul();
        ghoul2.setName("Rize");
        ghoul2.setHealth(2);
        ghoul2.setDependence(3);

        Ghoul ghoul3 = new Ghoul();
        ghoul3.setName("Jason");
        ghoul3.setHealth(2);
        ghoul3.setDependence(2);

// Asignación de minions a los demonios
        demon1.addMinion(demon2);
        demon1.addMinion(demon3);
        demon1.addMinion(ghoul2);
        demon1.addMinion(ghoul3);

// Creación de más humanos para ser minions de los demonios
        Human human2 = new Human();
        human2.setName("Delilah");
        human2.setLoyalty(1);
        human2.setHealth(2);

        Human human3 = new Human();
        human3.setName("Cain");
        human3.setLoyalty(2);
        human3.setHealth(2);

        Human human4 = new Human();
        human4.setName("Lilith");
        human4.setLoyalty(3);
        human4.setHealth(2);

        Human human5 = new Human();
        human5.setName("Lucifer");
        human5.setLoyalty(1);
        human5.setHealth(2);

// Asignación de más minions a los demonios
        demon.addMinion(human2);
        demon.addMinion(human3);
        demon.addMinion(demon2);
        demon2.addMinion(human4);
        demon3.addMinion(human5);

        weapon.getDocument().saveToDatabase(Weapon.class);
        weapon1.getDocument().saveToDatabase(Weapon.class);
        weapon2.getDocument().saveToDatabase(Weapon.class);
        armor.getDocument().saveToDatabase(Armor.class);
        armor1.getDocument().saveToDatabase(Armor.class);
        ability.getDocument().saveToDatabase(Talent.class);
        ability1.getDocument().saveToDatabase(Blessing.class);
        ability2.getDocument().saveToDatabase(Talent.class);
        characteristic.getDocument().saveToDatabase(Weakness.class);
        characteristic1.getDocument().saveToDatabase(Resistance.class);
        human.getDocument().saveToDatabase(Human.class);
        ghoul.getDocument().saveToDatabase(Ghoul.class);
        demon.getDocument().saveToDatabase(Demon.class);
        demon1.getDocument().saveToDatabase(Demon.class);
        demon2.getDocument().saveToDatabase(Demon.class);
        demon3.getDocument().saveToDatabase(Demon.class);
        ghoul2.getDocument().saveToDatabase(Ghoul.class);
        ghoul3.getDocument().saveToDatabase(Ghoul.class);
        human2.getDocument().saveToDatabase(Human.class);
        human3.getDocument().saveToDatabase(Human.class);
        human4.getDocument().saveToDatabase(Human.class);
        human5.getDocument().saveToDatabase(Human.class);
    }
}