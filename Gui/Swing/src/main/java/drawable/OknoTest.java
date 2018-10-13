package drawable;


import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OknoTest extends JFrame implements Runnable,KeyListener {

    protected static final Logger LOGGER = Logger.getLogger( Game.class.getName() );

    private Game game;


    public OknoTest(Game game){
        super("Test");
        //setName("test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,480));
        setResizable(false);
        //setLocationRelativeTo(null);
        requestFocus();
        addKeyListener(this);
        pack();
        setVisible(true);
        this.game=game;
    }



    public void oknoRepaint(BufferedImage img){
        this.getGraphics().drawImage(img, 0, 0,null );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        game.keyPressed(k);
        LOGGER.log(Level.FINE,"key pressed");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //player.klavesaPustena(e.getKeyCode());
        int k = e.getKeyCode();
        game.keyReleased(k);
        LOGGER.log(Level.FINE,"key released");

    }

    @Override
    public void run() {

    }
}
