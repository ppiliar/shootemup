import static org.junit.Assert.assertEquals;

import game_objects.*;
import org.junit.Ignore;
import org.junit.Test;


public class PlayerTest {

    @Ignore("need fix")
    @Test
    public void
    testHit() {
        Player p = new Player(new Position(0,0),new Body(0,0), 0);
        Bullet b = new Bullet(new Position(0,0),new Body(1,1),20, Direction.E ,10 , 10);
        p.hit(b);
        p.hit(b);
        assertEquals(60, p.getHp());
        p.hit(b);
        assertEquals(40, p.getHp());
        p.hit(b);
        assertEquals(20, p.getHp());
        p.hit(b);
        assertEquals(0, p.getHp());
        p.hit(b);
        assertEquals(0, p.getHp());
        assertEquals(false, p.isAlive());
    }

}
