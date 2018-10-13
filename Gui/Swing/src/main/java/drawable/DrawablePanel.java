package drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DrawablePanel extends JPanel {

    private BufferedImage img;
    public DrawablePanel(BufferedImage img){
        this.img = img;
        this.setPreferredSize(new Dimension(img.getHeight(),img.getHeight()));
        this.setOpaque(false);
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, null );

    }
}