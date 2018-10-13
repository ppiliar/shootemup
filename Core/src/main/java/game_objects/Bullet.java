package game_objects;

public class Bullet extends GameObject{
    private int dmg;
    private final Direction d;
    private float speed=2;
    private boolean kill = false;

    public Bullet(Position p, Body b, int dmg, Direction d, int Xoffset, int Yoffset) {
        super(p, b);
        this.dmg = dmg;
        this.d = d;
        switch (d){
            case E: p.setX(p.getX()+Xoffset);break;
            case W: p.setX(p.getX()-Xoffset);break;
            case N: p.setY(p.getY()-Yoffset);break;
            case S: p.setY(p.getY()+Yoffset);break;
            default: break;
        }
    }


    public int getDmg(){
        return dmg;
    }
    public void setDmg(int dmg){
        this.dmg = dmg;
    }
    public Direction getDirection(){return d;}

    public void setDirection(Direction d){
        switch (d){
            case E: setVelX(speed);break;
            case W: setVelX(-speed);break;
            case N: setVelY(-speed);break;
            case S: setVelY(speed);break;
            default: break;
        }
    }
    public void update(){
        setDirection(d);
        move();
    }
    public int collidedWith(GameObject o){

        if(o instanceof Player){
            ((Player) o).hit(this);
            return 1;
        }
        if(o instanceof GameObject){
            return 0;
        }
        return -1;
    }
    public void killed(){
        this.kill = true;
    }
    public boolean hasKilled(){
        return this.kill;
    }
}
