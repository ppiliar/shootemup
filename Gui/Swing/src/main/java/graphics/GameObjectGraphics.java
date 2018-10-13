package graphics;

import drawable.Drawable;
import game_objects.Body;
import game_objects.GameObject;
import game_objects.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameObjectGraphics extends GameObject implements Drawable {
    BufferedImage img;
   // GameObject gameObject;

    public GameObjectGraphics(Position position, Body body) {
        super(position,body);
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameObjectGraphics(GameObject gameObject){
        super(gameObject.getP(),gameObject.getB());
    }

    /*public GameObjectGraphics(GameObject g){
        this.gameObject=g;
    }*/


    @Override
    public void draw(Graphics g) {
        //g.drawImage(img,(int)this.getPosition().getX() , (int)this.getPosition().getY(),(int)this.getBody().getWidth(),(int)this.getBody().getHeight(), null);
        //g.drawImage(img,(int)gameObject.getPosition().getX() , (int)gameObject.getPosition().getY(),(int)gameObject.getBody().getWidth(),(int)gameObject.getBody().getHeight(), null);
    }
}
