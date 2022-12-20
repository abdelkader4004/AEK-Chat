package GUI;

import client.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.awt.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */
public class ConnectionPane extends Panneau implements ClientInterface {

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel namePane = new JPanel();
    JPanel passPane = new JPanel();
    JPanel submitPane = new JPanel();
    JComboBox textPseudo = new JComboBox();
    JLabel labPseudo = new JLabel();
    JLabel labPass = new JLabel();
    JButton btSubmit = new JButton();
    JPasswordField passWord = new JPasswordField();
    ObserverInterface observer;
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
    VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel passSubPane = new JPanel();
    JPanel nameSubPane = new JPanel();
    JLabel labCreate = new JLabel();
    Client client;//= new Client(this);
    BorderLayout borderLayout1 = new BorderLayout();
    //WaitPanel panimation;
    //ContactListPanel plist;
    AuthenticateTask authenticateTask;
    JPanel panEntete = new JPanel();
    JLabel labEntete = new JLabel();

    public ConnectionPane(ObserverInterface observer) {
        try {
            this.observer = observer;
            //plist = new ContactListPanel(observer);
            //panimation = new WaitPanel(100, 150, Math.PI / 4, Math.PI / 4, observer);
            jbInit(); ///observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void authentifier() {
        boolean b = false;
        String login = "";
        String pass = "";
        boolean ok = false;
        char[] input = passWord.getPassword();
        pass = String.valueOf(input);
        login = (String) textPseudo.getSelectedItem();
        authenticateTask = new AuthenticateTask(login, pass, observer);
        // cree une tache pour authentifier   et poster sur le worker

        ClientApp.worker.submit(authenticateTask);




    } //etablirUneConnexio

    void btSubmit_actionPerformed(ActionEvent e) {
        observer.startAnimationPane();
        client = new Client();
        observer.setClient(client);
        //client.creeConnexion();
        authentifier();

    }

    void labCreate_mouseClicked(MouseEvent e) {
        observer.removeConnectionPane();
        observer.addPaneCreation();


    }

    private void jbInit() throws Exception {
        this.add(panEntete, null);

        labCreate.setCursor(Cursor.getPredefinedCursor(12));
        textPseudo.setPreferredSize(new Dimension(300, 20));
        textPseudo.setEditable(true);
        labCreate.addMouseListener(new ConnectionPane_labCreate_mouseAdapter(this));
        //textPseudo.addKeyListener(new ConnectionPane_textPseudo_keyAdapter(this));
//        textPseudo.addActionListener(new PanneauConnexion_textPseudo_actionAdapter(this));
        this.setLayout(verticalFlowLayout2);
        passPane.setLayout(verticalFlowLayout5);
        this.setMaximumSize(new Dimension(2147483647, 2147483647));
        submitPane.setLayout(flowLayout1);
        namePane.setLayout(verticalFlowLayout4);
        btSubmit.setText("connexion");
        // btSubmit.setEnabled(false);
        btSubmit.addActionListener(new ConnectionPane_btSubmit_actionAdapter(this));
        labPseudo.setOpaque(false);
        labPseudo.setHorizontalAlignment(SwingConstants.CENTER);
        labPseudo.setText("pseudo");
        labPass.setOpaque(false);
        labPass.setToolTipText("");
        labPass.setHorizontalAlignment(SwingConstants.CENTER);
        labPass.setText("mot de passe");
        passWord.setPreferredSize(new Dimension(300, 20));
        passWord.addMouseListener(new ConnectionPane_passWord_mouseAdapter(this));
        passWord.addFocusListener(new ConnectionPane_passWord_focusAdapter(this));
        passWord.addInputMethodListener(new ConnectionPane_passWord_inputMethodAdapter(this));
        passWord.addKeyListener(new ConnectionPane_passWord_keyAdapter(this));
        passWord.addActionListener(new ConnectionPane_passWord_actionAdapter(this));
        verticalFlowLayout2.setAlignment(VerticalFlowLayout.TOP);
        labCreate.setOpaque(false);
        labCreate.setHorizontalAlignment(SwingConstants.CENTER);
        labCreate.setText("Creer un nouveau compte ");
        passSubPane.setOpaque(false);
        nameSubPane.setOpaque(false);
        namePane.setOpaque(false);
        passPane.setOpaque(false);
        submitPane.setOpaque(false);
        namePane.add(labPseudo, null);
        namePane.add(nameSubPane, null);
        nameSubPane.add(textPseudo, null);
        namePane.add(labCreate, null);
        this.add(namePane, null);
        this.add(passPane, null);
        passPane.add(labPass, null);
        passPane.add(passSubPane, null);
        passSubPane.add(passWord, null);
        this.add(submitPane, null);
        submitPane.add(btSubmit, null);
        panEntete.setOpaque(false);
        labEntete.setIcon(new ImageIcon(CreationPane.class.getResource("password.png")));
        panEntete.add(labEntete, null);
    }

    /**
     * creeConnexion
     */
    void textPseudo_actionPerformed(ActionEvent e) {
        /*    if (passWord.getText() != "") {
        btSubmit.setEnabled(true);
        }*/
    }

    public ConnectionPane() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void passWord_actionPerformed(ActionEvent e) {
    }

    void textPseudo_keyTyped(KeyEvent e) {
        if (passWord.getPassword().length != 0) {
            btSubmit.setEnabled(true);
        }
    }

    void passWord_keyTyped(KeyEvent e) {
    }

    void passWord_inputMethodTextChanged(InputMethodEvent e) {
    }

    void passWord_keyReleased(KeyEvent e) {
        // if(textPseudo.getSelectedItem().toString()!=null)
    }

    void passWord_keyPressed(KeyEvent e) {
        if ("".equals(textPseudo.getSelectedItem().toString())) {
            btSubmit.setEnabled(true);
        }
    }

    void passWord_focusGained(FocusEvent e) {
    }

    void passWord_mouseClicked(MouseEvent e) {
//btSubmit.setEnabled(true);
    }

    void textPseudo_keyPressed(KeyEvent e) {
    }
    /**
     * authentifier
     *
     * @param login String
     * @param motpass String
     * @return boolean
     */
}

class PanneauConnexion_btSubmit_actionAdapter
        implements java.awt.event.ActionListener {

    ConnectionPane adaptee;

    PanneauConnexion_btSubmit_actionAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btSubmit_actionPerformed(e);
    }
}

class PanneauConnexion_textPseudo_actionAdapter
        implements java.awt.event.ActionListener {

    ConnectionPane adaptee;

    PanneauConnexion_textPseudo_actionAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textPseudo_actionPerformed(e);
    }
}

class ConnectionPane_btSubmit_actionAdapter
        implements java.awt.event.ActionListener {

    ConnectionPane adaptee;

    ConnectionPane_btSubmit_actionAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btSubmit_actionPerformed(e);
    }
}

class ConnectionPane_passWord_actionAdapter
        implements java.awt.event.ActionListener {

    ConnectionPane adaptee;

    ConnectionPane_passWord_actionAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.passWord_actionPerformed(e);
    }
}

class ConnectionPane_textPseudo_keyAdapter
        extends java.awt.event.KeyAdapter {

    ConnectionPane adaptee;

    ConnectionPane_textPseudo_keyAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent e) {
        adaptee.textPseudo_keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        adaptee.textPseudo_keyPressed(e);
    }
}

class ConnectionPane_passWord_keyAdapter
        extends java.awt.event.KeyAdapter {

    ConnectionPane adaptee;

    ConnectionPane_passWord_keyAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent e) {
        adaptee.passWord_keyTyped(e);
    }

    public void keyReleased(KeyEvent e) {
        adaptee.passWord_keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        adaptee.passWord_keyPressed(e);
    }
}

class ConnectionPane_passWord_inputMethodAdapter
        implements java.awt.event.InputMethodListener {

    ConnectionPane adaptee;

    ConnectionPane_passWord_inputMethodAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void caretPositionChanged(InputMethodEvent e) {
    }

    public void inputMethodTextChanged(InputMethodEvent e) {
        adaptee.passWord_inputMethodTextChanged(e);
    }
}

class ConnectionPane_passWord_focusAdapter
        extends java.awt.event.FocusAdapter {

    ConnectionPane adaptee;

    ConnectionPane_passWord_focusAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void focusGained(FocusEvent e) {
        adaptee.passWord_focusGained(e);
    }
}

class ConnectionPane_passWord_mouseAdapter
        extends java.awt.event.MouseAdapter {

    ConnectionPane adaptee;

    ConnectionPane_passWord_mouseAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.passWord_mouseClicked(e);
    }
}

class ConnectionPane_labCreate_mouseAdapter extends java.awt.event.MouseAdapter {

    ConnectionPane adaptee;

    ConnectionPane_labCreate_mouseAdapter(ConnectionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.labCreate_mouseClicked(e);
    }
}
