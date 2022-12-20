
package GUI;

/**
 *
 * @author user
 */


import javax.swing.*;
import java.awt.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 * @author non attribuable
 * @version 1.0
 */

public class messagePane
    extends JPanel {
  JLabel SenderLogin = new JLabel();
  JLabel labMessage = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  public messagePane(Icon userIcon, String pseudo, String message,
                     Icon emoticone) {
    try {
      jbInit(userIcon, pseudo, message, emoticone);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit(Icon userIcon, String pseudo, String message,
                      Icon emoticone) throws Exception {

    SenderLogin.setText(pseudo);
    if (emoticone == null) {
      labMessage.setText(message);
    }
    else {
      labMessage.setIcon(emoticone);
      if (message != null) {
        labMessage.setText(message);
      }

    }
    this.setLayout(borderLayout1);
    this.add(SenderLogin, BorderLayout.NORTH);
    this.add(labMessage, BorderLayout.CENTER);
  }

}
