import game_objects.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NpcTest {

    @Test
    public void
    testHit() {
        Player p = new Player(new Position(0,0),new Body(0,0), 0);
        Bullet b = new Bullet(new Position(0,0),new Body(1,1),20, Direction.E,10,10);
        b.setDmg(50);
        p.hit(b);
        assertEquals(50, p.getHp());
        p.hit(b);
        assertEquals(0, p.getHp());
        p.hit(b);
        assertEquals(0, p.getHp());
        assertEquals(false, p.isAlive());

    }
}
