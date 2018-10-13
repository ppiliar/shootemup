import game_objects.Body;
import game_objects.GameObject;
import game_objects.Player;
import game_objects.Position;
import collision.Collision;
import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovementCollisionTest {

    @Ignore("need fix")
    @Test
    public void testMovement(){
        Player p1 = new Player(new Position(4f,4f),new Body(2f,2f), 0);

        GameObject wall = new GameObject(new Position(1,4),new Body(2,2)) ;

        p1.moveLeft();
        assertEquals(3.9f, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.8f, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.7, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.6, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.5f, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.4f, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.3, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.2, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3.1, p1.getPosition().getX(),0.001);
        p1.moveLeft();
        assertEquals(3f, p1.getPosition().getX(),0.001);

        assertFalse(Collision.checkCollision(p1, wall));

        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        p1.moveLeft();
        assertEquals(2f,p1.getPosition().getX(),0.001);
        assertTrue(Collision.checkCollision(p1,wall ));






        /*for(int i=0;i<50;i++) {
            p1.moveLeft();
            for (GameObject wall :  walls) {
                if (collision.Collision.collidedWith(p1, wall)) {
                    p1.moveBack();
                    break;
                }
            }

        }*/


    }

}
