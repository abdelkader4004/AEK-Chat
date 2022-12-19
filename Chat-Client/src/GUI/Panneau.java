package GUI;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */



import java.awt.*;
import java.awt.GradientPaint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;


public class Panneau   extends JPanel{
  JButton jButton1 = new JButton();

  public Panneau() {
    super();


  }

    @Override
 public void paintComponent(Graphics g) {

      Graphics2D g2d = (Graphics2D) g;
      GradientPaint gp = new GradientPaint(0, this.getHeight(),
                                            Color.WHITE,0,0 ,
                                            Color.orange,false);
      g2d.setPaint(gp);
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

    }


}
