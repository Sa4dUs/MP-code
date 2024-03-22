package server.minions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinionTest {

    @Test
    public void DemonHealthTess() {
        Demon d = new Demon();
        d.setHealth(3);

        List<Minion> demonMinions = new ArrayList<>();

        Human minion1 = new Human();
        minion1.setHealth(2);
        demonMinions.add(minion1);
        Human minion2 = new Human();
        minion2.setHealth(1);
        demonMinions.add(minion2);
        d.setMinions(demonMinions);

        assertSame(d.getHealth(), 6);
    }

}