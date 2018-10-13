package game_objects;

import game.Game;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trieda hraca ktora dedi z GameObject
 */
public class Player extends GameObject {

    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );

    private int hp=100;
    private int deaths =0;
    private int kills = 0;
    private float speed = 1;
    private Collectable collectable = null;
    private double collectableTime = 0;
    protected Direction direction = Direction.S;
    protected List<Bullet> bullets = new CopyOnWriteArrayList<>();
    private List<GameObject> gameObjects;

    private boolean leftFlag = false;
    private boolean rightFlag =false;
    private boolean upFlag =false;
    private boolean downFlag =false;

    private double firstTime= System.nanoTime()/1000000000f;
    private double lastTime=0;
    protected double animationDelta =0;
    //private double frame=0;

    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;
    private int shootKey;

    private Body originBody;
    //protected boolean readingList=false;

    public Player(Position p, Body b, int playerNo) {
        super(p,b);
        this.p_last=new Position(p.getX(),p.getY());
        this.originBody=new Body(b.getHeight(), b.getWidth());
    }

    public int getHp(){
        return hp;
    }

    public int getKills() {return kills;}

    /**
     * @Overriden v PlayerGraphics
     */
    public void shoot(){
        int Xoffset = (int)this.getBody().getWidth()/2;
        int Yoffset = (int)this.getBody().getHeight()/2;
        Bullet b = new Bullet(new Position(this.getPosition().getX(),this.getPosition().getY()),
                new Body(30,30),100, direction, Xoffset, Yoffset);
        bullets.add(b);


    }
    public void hit(Bullet b){
        if(hp>0)hp-=b.getDmg();

        if(!isAlive()){
            this.hp = 100;
            this.deaths++;
            b.killed();
            boolean colliding=false;

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
        }

    }
    //public void zober(){}

    public boolean isAlive(){
        if(hp>0){return true;}else{return false;}

    }



    public void update(List<GameObject> gameObjects){

        this.gameObjects=gameObjects;
        if(collectable!=null){
           collectable.getEffect().update(collectable);
        }
        setVelocity();
        move();
        lastTime=System.nanoTime()/1000000000f;
        animationDelta = Math.abs(firstTime-lastTime);
        animationDelta = animationDelta*3;
        if(animationDelta>2){
            firstTime=lastTime;
            animationDelta=0;
        }

        //LOGGER.info(bullets.toString());

        for (Bullet bullet:bullets) {
                 bullet.update();

            for (GameObject gameObject:gameObjects ) {
                if(this!=gameObject) {
                    if (collision.Collision.checkRect(gameObject, bullet)) {
                        int x = bullet.collidedWith(gameObject);
                        if (x >-1) {
                            if(bullet.hasKilled())this.kills++;
                            bullets.remove(bullet);
                            break;

                        }

                    }
                }
            }
        }
    }

    public void setDirection(Direction d){
        this.direction = d;
        LOGGER.log(Level.FINE,"Direction change {0}",direction);

    }
    public void setCollectable(Collectable collectable){
        if(this.collectable!=null) this.collectable.getEffect().end();
        collectable.updateTime();
        this.collectable = collectable;

       // collectableTime = System.nanoTime()/1000000000f;
        collectable.getEffect().effect(this);
    }
    public void collectableEnd(){
        this.collectable = null;
    }
    public int getDeaths(){
        return this.deaths;
    }

    public Position getCenter(){
        Position center = new Position(this.getPosition().getX()+(this.getBody().getWidth()/2),
                                        this.getPosition().getY()+(this.getBody().getHeight()/2));
        return center;
    }
    public Body getOriginBody(){
        return originBody;
    }

    public void setLeftFlag(boolean leftFlag) {
        if(leftFlag)clearFlags();
        this.leftFlag = leftFlag;
    }

    public void setRightFlag(boolean rightFlag) {
        if(rightFlag)clearFlags();
        this.rightFlag = rightFlag;
    }

    public void setUpFlag(boolean upFlag) {
        if(upFlag)clearFlags();
        this.upFlag = upFlag;
    }

    public void setDownFlag(boolean downFlag) {
        if(downFlag)clearFlags();
        this.downFlag = downFlag;
    }
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public void setVelocity(){
        stop();

        if(leftFlag)this.setVelX(-1*speed);

        if(rightFlag)this.setVelX(1*speed);

        if(upFlag)this.setVelY(-1*speed);

        if(downFlag)this.setVelY(1*speed);

    }

    public void clearFlags(){
        this.leftFlag=false;
        this.rightFlag=false;
        this.upFlag=false;
        this.downFlag=false;
    }

    public void setRightKey(int rightKey){
        this.rightKey=rightKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public void setShootKey(int shootKey){
        this.shootKey=shootKey;
    }

    public void keyPressed(int k) {
        if(k == leftKey) {setLeftFlag(true);setDirection(Direction.W);}
        if(k == rightKey) {setRightFlag(true);setDirection(Direction.E);}
        if(k == upKey) {setUpFlag(true);setDirection(Direction.N);}
        if(k == downKey) {setDownFlag(true);setDirection(Direction.S);}
        if(k == shootKey) {shoot();}

    }
    public void keyReleased(int k) {
        if (k == leftKey) setLeftFlag(false);
        if (k == rightKey) setRightFlag(false);
        if (k == upKey) setUpFlag(false);
        if (k == downKey) setDownFlag(false);
    }

}
