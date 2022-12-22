package dz.umab.chat.dskclient.GUI;


import javax.swing.*;
import java.awt.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 *
 * @author non attribuable
 * @version 1.0
 */

public class IconLabel extends JLabel {
    public IconLabel() {
        this.setBackground(Color.white);
        this.setEnabled(true);
        this.setFont(new java.awt.Font("Dialog", 0, 11));
        this.setDoubleBuffered(false);
        this.setMaximumSize(new Dimension(30, 30));
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(40, 40));
        this.setRequestFocusEnabled(false);
        this.setToolTipText("");
        this.setDisplayedMnemonic('0');
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.setIcon(new ImageIcon(IconLabel.class.getResource("delete.png")));
        this.setIconTextGap(0);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setFont(new java.awt.Font("Dialog", 0, 18));

    }

}
