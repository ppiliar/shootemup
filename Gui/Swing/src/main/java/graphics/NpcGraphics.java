package graphics;

import drawable.Drawable;
import game_objects.Body;
import game_objects.Npc;
import game_objects.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NpcGraphics extends Npc implements Drawable {

    BufferedImage img;

    public NpcGraphics(Position position, Body body) {
        super(position,body);
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/luigi.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics g) {

        g.drawImage(img,(int)this.getPosition().getX() , (int)this.getPosition().getY(),(int)this.getBody().getWidth(),(int)this.getBody().getHeight(), null);

    }
}
