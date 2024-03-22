package server.minions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinionTest {

    @Test
    public void DemonHealthTess() {
        Demon d = new Demon();
        d.setHealth(3);
        List<Minion> demonMinions = new ArrayList<>();
        d.setMinions(demonMinions);

        Human minion1 = new Human();
        minion1.setHealth(2);
        d.addMinions(minion1);
        Human minion2 = new Human();
        minion2.setHealth(1);
        d.addMinions(minion2);

        assertSame(d.getHealth(), 6);
    }

}