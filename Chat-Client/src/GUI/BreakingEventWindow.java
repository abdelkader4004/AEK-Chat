/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


//Create a Draggable JWindow
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import java.util.TimerTask;

public class BreakingEventWindow extends JWindow {

    private int X = 0;
    private int Y = 0;
    JPanel jPanel1 = new Panneau();
    JButton jButton1 = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JButton jButton2 = new JButton();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border1;
    TitledBorder titledBorder1;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    protected String alert,  info;
    String message;
    protected BreakingEventWindowInterface breakingEventWindowInterface;
    protected boolean confirmation = false;
    public BreakingEventWindow(String alert, String message) {
        this.alert = alert;
        this.message = message;
        setSize(300, 120);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width),
                (screenSize.height - frameSize.height) + 80);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); //An Exit Listener
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + (e.getX() - X), getLocation().y + (e.getY() - Y));
            }
        });

        //Print (X,Y) coordinates on Mouse Click

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                X = e.getX();
                Y = e.getY();

                System.out.println("The (X,Y) coordinate of window is (" + X + "," + Y +
                        ")");
            }
        });


        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(BorderFactory.createMatteBorder(6, 6, 6, 6,
                new Color(230, 230, 230)), "Urgent");

        jPanel1.setLayout(borderLayout1);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel3.setLayout(borderLayout2);
        jPanel1.setBorder(titledBorder1);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(null);
        jLabel1.setText(message);
        jLabel1.addMouseListener(new BreakingEventWindow_jLabel1_mouseAdapter(this));
        jLabel1.addMouseMotionListener(new BreakingEventWindow_jLabel1_mouseMotionAdapter(this));
        jLabel2.setCursor(Cursor.getPredefinedCursor(12));

        jLabel2.setText(alert);
        try {
            jLabel2.setIcon(new ImageIcon(new java.net.URL("file:///C:/Documents and Settings/1/jbproject/TestContactList/src/GUI/home.gif")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(BreakingEventWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);

        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jLabel1, BorderLayout.CENTER);
        jPanel3.add(jLabel2, BorderLayout.NORTH);
        this.validate();
        jPanel1.validate();
        jLabel1.setCursor(Cursor.getPredefinedCursor(12));
        new java.util.Timer().schedule(new Temporisateur(this), 0, 10);
        setVisible(true);
    }
    public BreakingEventWindow(String alert, String message, BreakingEventWindowInterface breakingEventWindowInterface, String info) {
        this( alert,  message);
        confirmation = true;
        this.info = info;
        jButton1.setText("Accepter");
        jButton1.addActionListener(new BreakingEventWindow_jButton1_actionAdapter(this));
        jPanel2.add(jButton1, null);
        jPanel2.add(jButton2, null);
        jButton2.setText("Refuser");
        jButton2.addActionListener(new BreakingEventWindow_jButton2_actionAdapter(this));
        this.breakingEventWindowInterface = breakingEventWindowInterface;
       
       /*
        setSize(300, 120);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width),
                (screenSize.height - frameSize.height) + 80);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); //An Exit Listener
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + (e.getX() - X), getLocation().y + (e.getY() - Y));
            }
        });

        //Print (X,Y) coordinates on Mouse Click

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                X = e.getX();
                Y = e.getY();

                System.out.println("The (X,Y) coordinate of window is (" + X + "," + Y +
                        ")");
            }
        });


        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(BorderFactory.createMatteBorder(6, 6, 6, 6,
                new Color(230, 230, 230)), "Urgent");

        jPanel1.setLayout(borderLayout1);
        jPanel2.setOpaque(false);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(borderLayout2);
        jPanel1.setBorder(titledBorder1);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(null);
        jLabel1.setText(message);
        jLabel1.addMouseListener(new BreakingEventWindow_jLabel1_mouseAdapter(this));
        jLabel1.addMouseMotionListener(new BreakingEventWindow_jLabel1_mouseMotionAdapter(this));
        jLabel2.setCursor(Cursor.getPredefinedCursor(12));

        jLabel2.setText(alert);
        try {
            jLabel2.setIcon(new ImageIcon(new java.net.URL("file:///C:/Documents and Settings/1/jbproject/TestContactList/src/GUI/home.gif")));
        } catch (MalformedURLException ex) {
            Logger.getLogger(BreakingEventWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);

        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jLabel1, BorderLayout.CENTER);
        jPanel3.add(jLabel2, BorderLayout.NORTH);
        this.validate();
        jPanel1.validate();
        jLabel1.setCursor(Cursor.getPredefinedCursor(12));
        new java.util.Timer().schedule(new Temporisateur(this), 0, 10);
        setVisible(true);*/
    }



    void jButton1_actionPerformed(ActionEvent e) {

        confirmation = false;
        breakingEventWindowInterface.reponse(true, info);
        this.setVisible(false);
    }

    void jButton2_actionPerformed(ActionEvent e) {

        breakingEventWindowInterface.reponse(false, info);
        this.setVisible(false);
    }

    void jLabel1_mouseMoved(MouseEvent e) {

        //  validate();
    }

    void jLabel1_mouseEntered(MouseEvent e) {
        jLabel1.setForeground(Color.ORANGE);
    }

    void jLabel1_mouseExited(MouseEvent e) {
        jLabel1.setForeground(Color.black);

    }
}

class BreakingEventWindow_jButton1_actionAdapter
        implements java.awt.event.ActionListener {

    BreakingEventWindow adaptee;

    BreakingEventWindow_jButton1_actionAdapter(BreakingEventWindow adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}

class BreakingEventWindow_jButton2_actionAdapter
        implements java.awt.event.ActionListener {

    BreakingEventWindow adaptee;

    BreakingEventWindow_jButton2_actionAdapter(BreakingEventWindow adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}

class BreakingEventWindow_jLabel1_mouseMotionAdapter
        extends java.awt.event.MouseMotionAdapter {

    BreakingEventWindow adaptee;

    BreakingEventWindow_jLabel1_mouseMotionAdapter(BreakingEventWindow adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        adaptee.jLabel1_mouseMoved(e);
    }
}

class BreakingEventWindow_jLabel1_mouseAdapter
        extends java.awt.event.MouseAdapter {

    BreakingEventWindow adaptee;

    BreakingEventWindow_jLabel1_mouseAdapter(BreakingEventWindow adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        adaptee.jLabel1_mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        adaptee.jLabel1_mouseExited(e);
    }
}

class Temporisateur extends TimerTask {

    BreakingEventWindow jWindow;
    int y = 620, delay = 0;
    boolean direction = true;

    /**
     * run
     */
    Temporisateur(BreakingEventWindow jWindow) {
        this.jWindow = jWindow;

    }

    public void run() {
        // System.out.println(jWindow.getLocation().y);
        delay++;
        if ((y < jWindow.getLocation().y)) {
            if (direction) {
                jWindow.setLocation(jWindow.getLocation().x,
                        jWindow.getLocation().y - 1);
            } else {
                jWindow.setLocation(jWindow.getLocation().x, jWindow.getLocation().y + 1);

            }
        } else if (delay > 500) {

            y -= 1;
            direction = false;
        }
        if (jWindow.getLocation().y > Toolkit.getDefaultToolkit().getScreenSize().height) {
            if (jWindow.confirmation) {
                jWindow.breakingEventWindowInterface.reponse(false, jWindow.info);
                jWindow.setVisible(false);
            }
            this.cancel();

        }
    }
}
