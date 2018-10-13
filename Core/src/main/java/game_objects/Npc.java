package game_objects;



public class Npc extends GameObject{

    private int hp=100;
    private int armor=100;
    public Npc(Position p, Body b) {
        super(p,b);

    }

    public void strielaj(){
        //Bullet b = new Bullet();
    }
    public void hit(Bullet b){
        if(armor>0){if(armor-b.getDmg()>=0){armor-=b.getDmg();}else{armor=b.getDmg()-armor;hp-=armor;}}else{
            if(hp>0)hp-=b.getDmg();
        }
    }

    public boolean isAlive(){
        if(hp>0){return true;}else{return false;}

    }


}
