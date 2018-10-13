package graphics;

import drawable.Drawable;
import game.Game;
import game_objects.Body;
import game_objects.Bullet;
import game_objects.Direction;
import game_objects.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

public class BulletGraphics extends Bullet implements Drawable{

    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );

    BufferedImage img;

    public BulletGraphics(Position position, Body body, int dmg, Direction d, int Xoffset, int Yoffset) {
        super(position,body,dmg,d,Xoffset,Yoffset);
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/shoot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawable=true;
    }


    public void draw(Graphics g) {
        LOGGER.finest("draw Bullet");
        g.drawImage(img,(int)this.getPosition().getX() , (int)this.getPosition().getY(),(int)this.getBody().getWidth(),(int)this.getBody().getHeight(), null);
    }
}
