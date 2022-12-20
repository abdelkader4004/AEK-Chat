package GUI;

import client.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.*;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mep.ui.component.CloseTabEvent;
import mep.ui.component.CloseTabListener;
import mep.ui.component.MTabbedPane;


/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */
public class MainFrame extends JFrame implements ObserverInterface {

    static Hashtable<Long, ChatPanel> discussionPaneList = new Hashtable<Long, ChatPanel>();
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenuItem MenuFileCreation = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JToolBar jToolBar = new JToolBar();
    ImageIcon image1;
    ImageIcon image2;
    ImageIcon image3;
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    MTabbedPane MainTabbedPane = new MTabbedPane(Color.WHITE, Color.WHITE);
    public static Client client;
    ConnectionPane connectionPane = new ConnectionPane(this);
    InfiniteProgressPanel infiniteProgressPanel;
    public static ContactListPanel contactListPanel;
    ChatPanel proom;
    CreationPane creationPane;

    public MainFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void MenuFileCreation_actionPerformed(ActionEvent e) {
        // new CreationPane();
    }

    void mainTabbedPane_TabClosed(CloseTabEvent event) {
     ChatPanel cp=  (ChatPanel) MainTabbedPane.getComponent(event.getTabIndex());
   

    }

    //Initialiser le composant
    private void jbInit() throws Exception {

        image1 = new ImageIcon(MainFrame.class.getResource("Grey.png"));
        //  image2 = new ImageIcon(client.MainFrame.class.getResource("closeFile.png"));
        //image3 = new ImageIcon(client.MainFrame.class.getResource("help.png"));
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(400, 600));
        this.setTitle("MagiChat");
        statusBar.setOpaque(false);
        statusBar.setText(" ");
        jMenuFile.setText("Fichier");
        jMenuFileExit.setText("Quitter");
        jMenuFileExit.addActionListener(new MainFrame_jMenuFileExit_ActionAdapter(this));
        MenuFileCreation.setText("Créer un Compte");
        MenuFileCreation.addActionListener(new MainFrame_MenuFileCreation_ActionAdapter(this));
        jMenuHelp.setText("Aide");
        jMenuHelpAbout.setText("A propos");
        jMenuHelpAbout.addActionListener(new MainFrame_jMenuHelpAbout_ActionAdapter(this));
        contentPane.setEnabled(true);
        jMenuFile.add(MenuFileCreation);
        jMenuFile.add(jMenuFileExit);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuHelp);
        this.setJMenuBar(jMenuBar1);
        contentPane.add(jToolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.WEST);
        MainTabbedPane.setOpaque(false);
        contentPane.add(MainTabbedPane, BorderLayout.CENTER);
        MainTabbedPane.add(connectionPane, "Connexion");
        MainTabbedPane.setIconAt(0, new ImageIcon(MainFrame.class.getResource("User.png")));
      //  MainTabbedPane.addCloseTabAction(listener);
    }

    //Op�ration Fichier | Quitter effectu�e
    public void jMenuFileExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    //Op�ration Aide | A propos effectu�e
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
        MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
    }

    //Red�fini, ainsi nous pouvons sortir quand la fen�tre est ferm�e
    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            jMenuFileExit_actionPerformed(null);
        }
    }

    /**
     * creeConnexion
     */
    public void creeConnexion() {
    }

    /**
     * setTitre
     *
     * @param s String
     */
    public void setTitre(String s) {
        this.setTitle(s);
    }

    /**
     * setClient
     */
    public void setClient() {
    }

    /**
     * setClient
     *
     * @param client Client
     */
    public void setClient(Client client) {
        MainFrame.client = client;
    }

    public void changeState(String pseudo, String etas) {
        contactListPanel.changeState(pseudo, etas);
    }

    /**
     * connexionOK
     *
     * @param pseudo String
     */
    public void connexionOK(String pseudo) {
        this.setTitle(pseudo);
        this.MainTabbedPane.removeAll();//remove(connectionPane);

    }

    public void removePane(JPanel p) {
        MainTabbedPane.remove(p);
    }

    public void addPane(JPanel p, String text) {
        MainTabbedPane.add(p, text);
        MainTabbedPane.setSelectedComponent(p);

    }

    public void startAnimationPane() {

        this.setGlassPane(infiniteProgressPanel = new InfiniteProgressPanel(this));
        infiniteProgressPanel.start();

    }

    public void removeConnectionPane() {

        MainTabbedPane.remove(connectionPane);
    }

    public void startListening() {

        ReceiveTask receiveTask = new ReceiveTask(this, client);
        ClientApp.worker.submit(receiveTask);
    }

    public void setContactsListPane() {
        contactListPanel = new ContactListPanel(this);
        MainTabbedPane.add(contactListPanel, "Liste des contacts");
    }

    public void addContact(int state, OurString name) {
        contactListPanel.addContact(state, name);
    }

    public void stopAnimation() {
        infiniteProgressPanel.stop();
    }

    public void addMessage(String mes, String idEmetteur) {
        try {
            proom = discussionPaneList.get(new Long(idEmetteur));
            if (proom == null) {
                proom = new ChatPanel(client.getPseudo(new Long(idEmetteur)), this);
                addChatPane(proom, client.getPseudo(new Long(idEmetteur)));
                System.out.println("getPseudo " + client.getPseudo(new Long(idEmetteur)) + "getPseudo " + idEmetteur);
                discussionPaneList.put(new Long(idEmetteur), proom);
            }
            if (!proom.isVisible()) {
                proom.setVisible(true);
                
            }
            proom.receiveMessage(mes, client.getPseudo(new Long(idEmetteur)));
            MainTabbedPane.setSelectedComponent(proom);
            System.out.println("idEmetteur= " + idEmetteur + " mes= " + mes);
        } catch (Exception e) {
            System.out.println("errer u ");
        }


    }

    public void addChatPane(ChatPanel p, String s) {

        //  MainTabbedPane.add(p, "chat with " + s);
        MainTabbedPane.insertTab("chat avec " + s, null, p, "Fermer", 1, MTabbedPane.WITH_CLOSE_CROSS);

        System.out.println(s + client.getID(s));
        discussionPaneList.put(client.getID(s), p);
        MainTabbedPane.setSelectedComponent(p);
    }

    public void addPaneCreation() {
        creationPane = new CreationPane(this);
        MainTabbedPane.add(creationPane, "Création de nouveau compte");
    }

    public void removePaneCreation() {
        MainTabbedPane.remove(creationPane);
    }

    public void addConnectionPane() {
        connectionPane = new ConnectionPane(this);
        MainTabbedPane.add(connectionPane, "Connexion");
    }

    public void result(int task, int result) {
        stopAnimation();
        try {
            if (task == 0) {//if task is authentication
                if (result == 1) {
                    removeConnectionPane();
                    // add plist au jtabbedpane
                    setContactsListPane();
                    // start l'ecoute
                    startListening();
                } else {
                    if (result == 0) {
                        JOptionPane.showMessageDialog(null, "Vos informations d'identification ne sont pas valides", "erreur", 1);
                    } else {
                        JOptionPane.showMessageDialog(null, "MagicChatServer n'est pas en service...", "erreur", 1);
                    }
                }
            } else {//if task is regestring
                if (result == -1) {
                    JOptionPane.showMessageDialog(null, "MagicChatServer n'est pas en service...", "erreur", 1);
                } else {
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "MagicChatServer Congratulation, Vous avez créé le compte avec success.", "information", 1);
                        removePaneCreation();
                        addConnectionPane();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ce compte existe déjà", "erreur", 1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * authentifier
     *
     * @param login String
     * @param motpass String
     * @return boolean
     */
}
class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {

    MainFrame adaptee;

    MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuFileExit_actionPerformed(e);
    }
}

class MainFrame_MenuFileCreation_ActionAdapter implements ActionListener {

    MainFrame adaptee;

    MainFrame_MenuFileCreation_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.MenuFileCreation_actionPerformed(e);
    }
}

class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {

    MainFrame adaptee;

    MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuHelpAbout_actionPerformed(e);
    }
}
class MainFrame_MainTabbedPane_CloseTabListener implements CloseTabListener{
     MainFrame adaptee;
MainFrame_MainTabbedPane_CloseTabListener(MainFrame adaptee) {
        this.adaptee = adaptee;
    }
    public void closeAction(CloseTabEvent event) {
        adaptee.mainTabbedPane_TabClosed(event);
    }

}