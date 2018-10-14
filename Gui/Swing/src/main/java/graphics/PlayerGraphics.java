package graphics;


import drawable.Drawable;
import game_objects.Body;
import game_objects.Bullet;
import game_objects.Player;
import game_objects.Position;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import state_manager.XmlReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.PI;

public class PlayerGraphics extends Player implements Drawable {

    BufferedImage img;
    BufferedImage pgun;
    Map<String,BufferedImage> playerSprites = new HashMap<String, BufferedImage>();

    public PlayerGraphics(Position position, Body body, int playerNo) {
        super(position,body, playerNo);
        try {
            // XmlReader xmlReader = new XmlReader();
            Document players = XmlReader.readFile("data/players.xml");
            NodeList playersList = players.getElementsByTagName("player");
            Element player = (Element)playersList.item(playerNo);
            NodeList playerNodes = player.getElementsByTagName("character");


            img = ImageIO.read(this.getClass().getResourceAsStream("/images/sprites.png"));
            loadGraphics(playerNodes.item(0).getTextContent());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        drawable= true;
    }
    @Override
    public void shoot(){
        int Xoffset = (int)this.getBody().getWidth()/2;
        int Yoffset = (int)this.getBody().getHeight()/2;
        Bullet b = new BulletGraphics(this.getCenter(),new Body(10,10),
                200, direction, Xoffset, Yoffset);
        bullets.add(b);

    }

    public void draw(Graphics g){
        int pos_x = (int)this.getPosition().getX();
        int pos_y = (int)this.getPosition().getY();
        int width = (int)this.getBody().getWidth();
        int height = (int)this.getBody().getHeight();

        // zaujimavy foreach
        //playerSprites.forEach((k,v) -> g.drawImage(v, 0, 0,  null));

        if(getVelX()==0 && getVelY()==0){
            switch (direction){
                case S:g.drawImage(playerSprites.get("down"), pos_x, pos_y, width, height, null);break;
                case W:g.drawImage(playerSprites.get("left"), pos_x, pos_y, width, height, null);break;
                case E:g.drawImage(playerSprites.get("right"), pos_x, pos_y, width, height, null);break;
                case N:g.drawImage(playerSprites.get("up"), pos_x, pos_y, width, height, null);break;
                default:break;
            }

        }else{
            switch (direction) {
                case S:
                    g.drawImage(playerSprites.get("down"), pos_x, pos_y, width, height, null);
                    break;
                case W:
                    g.drawImage(playerSprites.get("left"), pos_x, pos_y, width, height, null);
                    break;
                case E:
                    g.drawImage(playerSprites.get("right"), pos_x, pos_y, width, height, null);
                    break;
                case N:
                    g.drawImage(playerSprites.get("up"), pos_x, pos_y, width, height, null);
                    break;
            }
        }

        for (Bullet b:bullets) {
            ((BulletGraphics)b).draw(g);
        }

    }

    public void loadGraphics(String spriteName){
        Document spritesXml = XmlReader.readFile("data/sprites.xml");
        NodeList spritesList = spritesXml.getElementsByTagName("SubTexture");
        for (int i=0; i<spritesList.getLength(); i++) {
            Element sprite = (Element) spritesList.item(i);
            String name = sprite.getAttribute("name");
            LOGGER.info(name);
            if(name.equals(spriteName+"_gun.png")){
                int x = Integer.parseInt(sprite.getAttribute("x"));
                int y = Integer.parseInt(sprite.getAttribute("y"));
                int w = Integer.parseInt(sprite.getAttribute("width"));
                int h = Integer.parseInt(sprite.getAttribute("height"));
                pgun = img.getSubimage(x, y, w, h);
                break;
            }
        }
        // treba vytvorit vasciu plochu aby pri otacani neorezalo casti obrazka
        BufferedImage origin = new BufferedImage(51, 45, BufferedImage.TRANSLUCENT);
        origin.getGraphics().drawImage(pgun, 0, 0,  null);
        playerSprites.put("right", origin);
        BufferedImage imgTf =  rotateSprite(PI/2d, origin);
        playerSprites.put("down", imgTf);
        imgTf = rotateSprite(PI, origin);
        playerSprites.put("left", imgTf);
        imgTf = rotateSprite(PI*(3d/2d), origin);
        playerSprites.put("up", imgTf);

    }

    /**
     * otocit img o pozadovany uhol
     * @param angle
     * @param img
     * @return rotated image
     */
    public BufferedImage rotateSprite(Double angle, BufferedImage img){
        AffineTransform tx = new AffineTransform();
        tx.rotate(angle, img.getWidth() / 2f, img.getHeight() / 2f);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        img = op.filter(img, null);
        return img;
    }

    public BufferedImage getIcon(){
        return playerSprites.get("down");
    }
}
