package com.modscleo4.ui.component;

import javax.swing.*;
import java.awt.*;

public class JImageView extends JLabel {
    public JImageView(Image image) {
        super(new ImageIcon(image));
    }
}
