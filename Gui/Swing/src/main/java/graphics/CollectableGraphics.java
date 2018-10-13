package graphics;

import drawable.Drawable;
import game.Game;
import game_objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class CollectableGraphics extends Collectable implements Drawable {
    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );
    BufferedImage img;

    public CollectableGraphics(Body b, String imgName, Effect effect, List<GameObject> gameObjects) {
        super(b,imgName,effect,gameObjects);
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/"+imgName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawable=true;
        time = System.nanoTime()/1000000000f;
        LOGGER.fine("collectible spawned");
    }


    public void draw(Graphics g) {
        LOGGER.finest("draw collectable");
        g.drawImage(img,(int)this.getPosition().getX() , (int)this.getPosition().getY(),(int)this.getBody().getWidth(),(int)this.getBody().getHeight(), null);
    }
}
