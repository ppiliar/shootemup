package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RadioButtonGraphics extends JRadioButton {

    public RadioButtonGraphics(String s)  {
        super();
        setText(s);
        setForeground(Color.WHITE);
        try {
            setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/rbt.png"))));
            setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/rbtc.png"))));
            setPressedIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/rbt.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBorder(BorderFactory.createEmptyBorder());
        setFocusable(false);
        setOpaque(false);
    }
}
