package game_objects;

import java.util.Random;

/**
 * Trieda na pracu  s poziciou
 */
public class Position {

    private float x;
    private float y;
    private Random rand = new Random();

    public Position(float x, float y){
        this.x=x;
        this.y=y;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void setXY(float x, float y){
        this.x=x;
        this.y=y;
    }
    public void setRandom(int maxWidth,int maxHeight){
        setXY(rand.nextInt(maxWidth),rand.nextInt(maxHeight) );
    }
}
