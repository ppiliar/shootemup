package drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

public class Frame2 extends JFrame{

    private BufferedImage img;

    public Frame2(){
        super("Frame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,480));
        setResizable(false);
        //setLocationRelativeTo(null);
        requestFocus();
        //addKeyListener(this);
        pack();
        setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }



    public void framerepaint(BufferedImage img){
        // this.img = img;

        this.getGraphics().drawImage(img, 0, 0, null );
        System.out.println("repaint");
    }




}
