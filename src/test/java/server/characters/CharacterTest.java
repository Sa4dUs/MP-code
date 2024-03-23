package server.characters;

import org.junit.jupiter.api.Test;
import server.minions.Demon;
import server.minions.Human;
import server.minions.Minion;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    public void testMinionsKilled1()
    {
        Character c1 = new Character();

        Human human1 = new Human();
        human1.setHealth(2);

        Human human2 = new Human();
        human2.setHealth(1);

        Human human3 = new Human();
        human3.setHealth(3);

        Demon demon1 = new Demon();
        demon1.setHealth(3);

        Demon demon2 = new Demon();
        demon2.setHealth(1);

        demon1.addMinion(human1);

        demon1.addMinion(demon2);

        c1.addMinion(demon1);
        c1.addMinion(human2);
        c1.addMinion(human3);

        assertEquals(2, c1.calculateMinionsKilledAfterDamage(5));

    }

    @Test
    public void testMinionsKilled2()
    {
        Character c1 = new Character();

        Human human1 = new Human();
        human1.setHealth(2);

        Human human2 = new Human();
        human2.setHealth(1);

        Human human3 = new Human();
        human3.setHealth(3);

        Demon demon1 = new Demon();
        demon1.setHealth(3);

        Demon demon2 = new Demon();
        demon2.setHealth(1);

        demon1.addMinion(human1);

        demon1.addMinion(demon2);

        c1.addMinion(demon1);
        c1.addMinion(human2);
        c1.addMinion(human3);

        assertEquals(5, c1.calculateMinionsKilledAfterDamage(10));

    }

    @Test
    public void testMinionsKilled3()
    {
        Character c1 = new Character();

        Human human1 = new Human();
        human1.setHealth(2);

        Human human2 = new Human();
        human2.setHealth(1);

        Human human3 = new Human();
        human3.setHealth(3);

        Demon demon1 = new Demon();
        demon1.setHealth(3);

        Demon demon2 = new Demon();
        demon2.setHealth(1);

        demon1.addMinion(human1);

        demon1.addMinion(demon2);

        c1.addMinion(demon1);
        c1.addMinion(human2);
        c1.addMinion(human3);

        assertEquals(0, c1.calculateMinionsKilledAfterDamage(2));

    }

}