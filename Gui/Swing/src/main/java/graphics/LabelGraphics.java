package graphics;

import javax.swing.*;
import java.awt.*;

public class LabelGraphics extends JLabel {

    public LabelGraphics(String s){
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        setFocusable(false);
        setForeground(Color.WHITE);
        setText(s);
    }

}
