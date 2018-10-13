package game;

import drawable.OknoTest;
import game_objects.*;
import drawable.Drawable;
import graphics.CollectableGraphics;
import graphics.GameObjectGraphics;
import graphics.NpcGraphics;
import graphics.PlayerGraphics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import state_manager.XmlReader;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


public class GameGraphics extends Game implements Drawable {

    public static final int MAX_WIDTH= OknoTest.WIDTH;
    public static final int MAX_HEIGHT=OknoTest.HEIGHT;
    BufferedImage background;


    public GameGraphics(){
        super();
        XmlReader xmlReader = new XmlReader();
        Document mapsData = xmlReader.readFile("data/maps.xml");
        NodeList maps = mapsData.getElementsByTagName("map");
        for (int i=0;i<maps.getLength();i++){
            Element e = (Element) maps.item(i);

            String name = e.getAttribute("name");
            int tile = Integer.parseInt(e.getAttribute("tile"));
            if(name.equals(this.mapName)){
                NodeList objects = e.getElementsByTagName("object");
                for (int j=0;j<objects.getLength();j++){
                    Element object = (Element)objects.item(j);
                    int x1 = Integer.parseInt(object.getAttribute("x1"));
                    int y1 = Integer.parseInt(object.getAttribute("y1"));
                    int x2 = Integer.parseInt(object.getAttribute("x2"));
                    int y2 = Integer.parseInt(object.getAttribute("y2"));
                    x2++;
                    y2++;
                    Position p = new Position(x1*tile, y1*tile);
                    Body b = new Body((y2-y1)*tile,(x2-x1)*tile);
                    gameObjects.add(new GameObject(p,b));
                }
            }
        }
        addBackground(600, 400, mapName);
        addWalls();

    }

    /*public GameObject createGameObject(int x, int y, int h, int w) {
        return new GameObjectGraphics(new Position(x,y),new Body(h,w));
    }*/
    public Player createPlayer(int x, int y, int h, int w, int playerNo) {
        return new PlayerGraphics(new Position(x,y),new Body(h,w), playerNo);
    }
    public Collectable createCollectable(CollectableModel cm){
        return new CollectableGraphics(new Body(25, 25), cm.getImgName(), cm.getEffect(), gameObjects);
    }
    public void addBackground(int x,int y, String source){
        background = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
        try {

            background = ImageIO.read(this.getClass().getResourceAsStream("/"+source+".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        //((PlayerGraphics)player).draw(g);
        for (GameObject gameObject:gameObjects ) {
            if(gameObject.isDrawable())((Drawable)gameObject).draw(g);
        }

    }
}
