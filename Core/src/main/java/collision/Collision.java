package collision;

import game_objects.Body;
import game_objects.GameObject;
import game_objects.Position;

/**
 * Trieda collision.Collision zistuje koliziu dvoch objektov
 */
public final class Collision {

    public static boolean checkCollision(GameObject o1, GameObject o2){

        Body b1=o1.getBody();
        Position p1=o1.getPosition();
        Body b2=o2.getBody();
        Position p2=o2.getPosition();

        float width1 =b1.getWidth();
        float height1 = b1.getHeight();
        float width2 =b2.getWidth();
        float height2 = b2.getHeight();

        float x1=p1.getX();
        float y1=p1.getY();
        float x2=p2.getX();
        float y2=p2.getY();



        return (Math.abs(x1 - x2) * 2 < (width1 + width2)) &&  (Math.abs(y1 - y2) * 2 < (height1 + height2));


    }

    public static boolean checkRect(GameObject o1, GameObject o2)
    {
        float amaxx=o1.getPosition().getX()+o1.getBody().getWidth();
        float amaxy=o1.getPosition().getY()+o1.getBody().getHeight();
        float aminy=o1.getPosition().getY();
        float aminx=o1.getPosition().getX();
        float bminx=o2.getPosition().getX();
        float bminy=o2.getPosition().getY();
        float bmaxx=o2.getPosition().getX()+o2.getBody().getWidth();
        float bmaxy=o2.getPosition().getY()+o2.getBody().getHeight();


        float d1x = bminx - amaxx;
        float d1y = bminy - amaxy;
        float d2x = aminx - bmaxx;
        float d2y = aminy - bmaxy;



        if (d1x > 0.0f || d1y > 0.0f)
            return false;

        if (d2x > 0.0f || d2y > 0.0f)
            return false;


        return true;
    }



}
