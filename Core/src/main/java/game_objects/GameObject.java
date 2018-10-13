package game_objects;

/**
 * Trieda ktoru bude dedit kazdy herny objekt, uklada poziciu a telo objektu
 */
public class GameObject {
    protected Position p;
    protected Position p_last;
    protected Body b;
    private float velX=0;
    private float velY=0;
    protected boolean drawable=false;

    public GameObject(Position p, Body b) {
        this.p = p;
        this.b = b;
        this.p_last=new Position(p.getX(),p.getY());
    }
    public Body getBody() {
        return b;
    }

    public Position getPosition() {
        return p;
    }
    private Position getLastPosition() {
        return p_last;
    }

    public void setP(Position p) {
        this.p = p;
    }

    public void setB(Body b) {
        this.b = b;
    }

    public void moveUp(){
        p_last.setXY(p.getX(),p.getY() );
        p.setY(p.getY()+velX);
    }
    public void moveDown(){
        p_last.setXY(p.getX(),p.getY() );
        p.setY(p.getY()+velX);
    }
    public void moveRight(){
        p_last.setXY(p.getX(),p.getY() );
        p.setX(p.getX()+velY);
    }
    public void moveLeft(){
        p_last.setXY(p.getX(),p.getY() );
        p.setX(p.getX()+velY);
    }
    public void moveBack(){
        p.setXY(p_last.getX(), p_last.getY());
        stop();
    }

    /**
        //TODO trhavy pohyb odstranit
     */


    public void setVelX(float f){
            this.velY=0;
            this.velX=f;

    }
    public void setVelY(float f){
            this.velX=0;
            this.velY=f;

    }
    public void move(){
        p_last.setXY(p.getX(),p.getY() );
        p.setX(p.getX()+velX);
        p.setY(p.getY()+velY);
    }
    public void stop(){
        this.velX=0;
        this.velY=0;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public boolean isDrawable() {
        return drawable;
    }

    public Position getP() {   return p;  }

    public Body getB() { return b;  }
}

