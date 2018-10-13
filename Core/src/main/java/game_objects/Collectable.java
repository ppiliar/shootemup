package game_objects;

import java.util.List;



public class Collectable extends GameObject{

    private String imgName;
    private Effect effect;
    protected double time;
    public Collectable(Body b, String imgName, Effect effect, List<GameObject> gameObjects) {
        super(new Position(0, 0),b);
        this.imgName = imgName;
        this.effect = effect;
        boolean colliding;
        do {
            colliding = false;
            this.getPosition().setRandom(800, 480);
            for (GameObject gameObject : gameObjects) {
                if(this!=gameObject) {
                    colliding = collision.Collision.checkRect(this, gameObject);
                    if (colliding) break;
                }
            }
        }while(colliding);
        time = System.nanoTime()/1000000000f;
    }

    public String getImgName(){
        return this.imgName;
    }
    public Effect getEffect(){
        return this.effect;
    }
    public double getTime(){
        return this.time;
    }
    public void setTime(double time){
        this.time = time;
    }
    public void updateTime(){
        this.time = System.nanoTime()/1000000000f;
    }

}
