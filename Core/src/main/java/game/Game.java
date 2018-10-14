package game;

import game_objects.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );


    protected Player player;
    protected GameObject go;
    protected List<GameObject> walls = new ArrayList<>();
    protected List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    protected LevelManager lvlManager;
    protected game.Level gameLevel;
    protected List<Player> players = new ArrayList<>();
    protected List<CollectableModel> collectableModels = new ArrayList<>();
    protected String mapName;

    private Properties prop = new Properties();
    private Random randomGenerator = new Random();

    public Game() {


        LOGGER.log(Level.INFO,"Game created");


        try {
            FileInputStream input = new FileInputStream("live.properties");
            prop.load(input);
            loadCollectables();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        lvlManager = new LevelManager();
        gameLevel =lvlManager.getCurrentLevel();
        addObjects();

        this.mapName = prop.getProperty("map");

        int noPlayers = Integer.parseInt(prop.getProperty("no_players"));
        for(int i=0;i<noPlayers;i++) {
            addPlayer(50+100*i, 50+100*i, 50, 44, i);
        }

        //System.out.println(Arrays.toString(collectables.toArray()));
    }

    public void createGame(){

    }


    public void addPlayer(int x,int y,int w,int h, int playerNo){
        //createPlayer(int x,int y,int w,int h);
        Player player = createPlayer(x, y, w, h, playerNo);
        players.add(player);
        gameObjects.add(player);

        /*player = new Player(p,b);
        players.add(player);*/
        PropertiesManager pm=new PropertiesManager();
        prop=pm.getProperties("player"+playerNo+".properties");
        String up=prop.getProperty("up");
        String down = prop.getProperty("down");
        String left = prop.getProperty("left");
        String right = prop.getProperty("right");
        String shoot = prop.getProperty("shoot");
        Field f = null;
        try {
            f = KeyEvent.class.getField(up);
            player.setUpKey( f.getInt(null));
            f = KeyEvent.class.getField(down);
            player.setDownKey( f.getInt(null));
            f = KeyEvent.class.getField(right);
            player.setRightKey( f.getInt(null));
            f = KeyEvent.class.getField(left);
            player.setLeftKey( f.getInt(null));
            f = KeyEvent.class.getField(shoot);
            player.setShootKey( f.getInt(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    /**
     * Vytvaranie objektov
     */


    public void addWalls(){
        gameObjects.add(createGameObject(0, 0, 1, 800));
        gameObjects.add(createGameObject(0, 480, 1, 800));
        gameObjects.add(createGameObject(0, 0, 480, 1));
        gameObjects.add(createGameObject(800, 0, 480, 1));
    }

    /**
     * Vytvori GameObject
     * tato metoda bude overiden v graphics tym padom nebude musiet byt logika v graphics
     * @param x
     * @param y
     * @param h
     * @param w
     * @return
     */
    public GameObject createGameObject(int x, int y, int w, int h) {
        return new GameObject(new Position(x,y),new Body(w,h));
    }
    public Player createPlayer(int x, int y, int w, int h, int playerNo){
        return new Player(new Position(x,y),new Body(w,h), playerNo);
    }
    public Collectable createCollectable(CollectableModel cm){
        return new Collectable(new Body(25, 25), cm.getImgName(), cm.getEffect(), gameObjects);
    }
    public void removeCollectable(Collectable c){
        this.gameObjects.remove(c);
    }

    public void loadCollectables() throws ScriptException, IOException {
        // priprav prostredie na spustanie skriptov
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.put("game", this);
        // nacitaj skript zo suboru a spusti ho
        URL url = ClassLoader.getSystemResource("collectables.js");
        try {
            engine.eval(new InputStreamReader(url.openStream()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void addObjects() {


        //XmlReader xmlReader = new XmlReader();
        PropertiesManager pm = new PropertiesManager();
        Properties lvl = new Properties();
        lvl = pm.getProperties("lvl1.properties");
        String position = lvl.getProperty("object.position");
        //float f = Float.parseFloat(s);
        float px = 0;
        float py = 0;
        if (position != null) {
            String pos[] = position.split(",");
            px = Float.parseFloat(pos[0]);
            py = Float.parseFloat(pos[1]);
        }
        //System.out.println(x+" "+y);

        String body = lvl.getProperty("object.body");
        float bx = 0;
        float by = 0;
        if (body != null) {
            String bod[] = body.split(",");
            bx = Float.parseFloat(bod[0]);
            by = Float.parseFloat(bod[1]);
        }

        if (position != null && body != null)
            gameObjects.add(new GameObject(new Position(px, py), new Body(bx, by)));

       /* for(int i=0;i<)
        gameObjects.add(new GameObject(new, ))*/
    }

    public void addCollectableModel(String imgName, Effect effect){
        CollectableModel c = new CollectableModel(imgName, effect);
        //Collectable c = createCollectable(new Body(20, 20), imgName, effect, gameObjects);

        this.collectableModels.add(c);
    }
    public void spawnCollectable(){
        int index = randomGenerator.nextInt(collectableModels.size());
        CollectableModel cm = collectableModels.get(index);
        Collectable c = createCollectable(cm);
        c.updateTime();
        gameObjects.add(c);
    }

    public void update(){
        //player.update(walls);
        LOGGER.log(Level.FINEST,"Game update");
        for(Player p:players){
            p.update(gameObjects);
            for (GameObject g:gameObjects ) {
                if(g!=p){
                    if( collision.Collision.checkRect(p, g)){
                        if(g instanceof Collectable){
                            p.setCollectable((Collectable)g);
                            gameObjects.remove(g);
                        }else {
                            p.moveBack();
                        }
                    }
                }

            }
        }
        for(GameObject g:gameObjects){
            if(g instanceof Collectable){
                ((Collectable) g).getEffect().update((Collectable) g);
            }
        }
    }
    public int getMostKills(){
        int kills=0;
        for(Player p:players){
            if(p.getKills()>kills){
                kills=p.getKills();
            }
        }
        return kills;
    }
    public List<Player> getPlayers(){
        return this.players;
    }
    public void keyPressed(int k) {
        for(Player p:players){
            p.keyPressed(k);
        }
    }
    public void keyReleased(int k) {
        for(Player p:players){
            p.keyReleased(k);
        }
    }

}
