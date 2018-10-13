package drawable;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger( Frame.class.getName() );
    private BufferedImage img;

    public Frame(){
        super("Frame");
        //this.run();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(800,480));
        setResizable(false);
        //setLocationRelativeTo(null);
        requestFocus();
        //addKeyListener(this);
        pack();

        //setVisible(false);
    }



    public void frameRepaint(BufferedImage img){
       this.getContentPane().getGraphics().drawImage(img, 0, 0, null );
    }


/*
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        //game.keyPressed(k);
        LOGGER.log(Level.INFO,"key pressed");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        //game.keyReleased(k);
        LOGGER.log(Level.FINE,"key released");

    }
*/



}
