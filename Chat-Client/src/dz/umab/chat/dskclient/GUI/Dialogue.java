package GUI;

import java.awt.*;
import javax.swing.*;



public class Dialogue extends JDialog {
  JPanel panel1 = new AjouterPane(this);
  BorderLayout borderLayout1 = new BorderLayout();

  public Dialogue( String title, boolean modal) {
   // super(frame, title, modal);
    try {
      jbInit();

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Dialogue() {
   // this(null, "", false);
    
  }

  private void jbInit() throws Exception {

    getContentPane().add(panel1);
  }
}