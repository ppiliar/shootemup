package game;

import drawable.Frame;
import drawable.OknoTest;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

public class GameLoop implements Runnable,KeyListener {

    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );

    private Thread thread;
    volatile private boolean running = false;
    private final double UPDATE_CAP = 1f/120f;

    private int time=0;
    private double allTime = 0;
    private int kills=0;
    private final double COLLECTIBLE_SPAWN_TIME = 10f;
    private double timeFromSpawn = 0;

    private GameGraphics game;
    private Frame frame;


    private BufferedImage b=new BufferedImage(800,480, BufferedImage.TYPE_INT_RGB);

    public GameLoop(Frame frame, GameGraphics game, int time, int kills){
        this.time=time;
        this.kills=kills;
        if(time==0 && kills==0){
            this.time = 120;
        }
        this.frame = frame;
        frame.addKeyListener(this);
        this.game =  game;
    }


    public void run(){

        running = true;

        boolean render = false;
        double nano = 1000000000.0;
        double firstTime = 0;
        double lastTime = System.nanoTime()/nano;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;


        while(running){

            render = false;

            firstTime = System.nanoTime()/nano;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            if(timeFromSpawn>COLLECTIBLE_SPAWN_TIME) {
                game.spawnCollectable();
                timeFromSpawn=0;
            }else {
                timeFromSpawn += passedTime;
            }

            // ukoncenie hry po case alebo killoch
            if( time!= 0 ) {
                allTime += passedTime;
                if (allTime > time) {
                    running = false;
                    return;
                }
            }else{
                if(game.getMostKills()>=kills){
                    running = false;
                    return;
                }
            }
            //render ak je co zmenit
            while(unprocessedTime >= UPDATE_CAP){
                unprocessedTime -= UPDATE_CAP;
                render = true;

                game.update();
                if(frameTime >= 1.0){
                    frameTime = 0;
                    fps = frames;
                    frames = 0;

                }
            }
            if(render){
                game.draw(b.getGraphics());
                frame.frameRepaint(b);

                frames++;
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Interrupted!", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(k==KeyEvent.VK_ESCAPE){frame.removeKeyListener(this);running=false;return;}
        game.keyPressed(k);
        LOGGER.log(FINE,"key "+KeyEvent.getKeyText(k)+" pressed in gameloop");
    }

    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        game.keyReleased(k);
        LOGGER.log(FINE,"key "+KeyEvent.getKeyText(k)+" released in gameloop");
    }

}
