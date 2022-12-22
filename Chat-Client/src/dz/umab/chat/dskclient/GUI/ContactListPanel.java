package dz.umab.chat.dskclient.GUI;

import dz.umab.chat.dskclient.client.ObserverInterface;
import dz.umab.chat.dskclient.client.OurString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci\uFFFDt\uFFFD : </p>
 *
 * @author non attribuable
 * @version 1.0
 */
public class ContactListPanel extends Panneau implements BreakingEventWindowInterface {

    final static int ICON_COLUMN_WIDTH = 30;
    public ContactListTableModel model = new ContactListTableModel();
    JScrollPane jScrollPane1 = new JScrollPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JTable table = new JTable(model);
    JPanel jPanel1 = new JPanel();
    JButton btAjouter = new JButton();


    ObserverInterface observer;
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem discutionCreateMenuItem = new JMenuItem();
    JMenuItem contactDeleteMenuItem = new JMenuItem();
    JMenuItem fileSendMenuItem = new JMenuItem();

    public ContactListPanel(ObserverInterface observer) {

        try {
            this.observer = observer;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void contactDeleteMenuItem_actionPerformed(ActionEvent e) {
        model.removeContact(table.getSelectedRow());
        MainFrame.client.WriteMessage("ContactDelete" + "," + MainFrame.client.getID(model.getPseudo(table.getSelectedRow())));
        model.fireTableDataChanged();
    }

    void discutionCreateMenuItem_actionPerformed(ActionEvent e) {
        if (MainFrame.discussionPaneList.get(MainFrame.client.getID(model.getPseudo(table.getSelectedRow()))) == null) {
            ChatPanel proom = new ChatPanel(model.getPseudo(table.getSelectedRow()), observer);
            observer.addChatPane(proom, model.getPseudo(table.getSelectedRow()));
        }
    }

    void fileSendMenuItem_actionPerformed(ActionEvent e) {
        FileSendPane fileSendPane = new FileSendPane(model.getPseudo(table.getSelectedRow()));
        observer.addPane(fileSendPane, "Envoi de fichier à " + model.getPseudo(table.getSelectedRow()));
    }

    void table_actionPerformed(ActionEvent e) {
        System.out.println("table click");
    }

    void table_mouseClicked(MouseEvent e) {
        if (model.getState(table.getSelectedRow()) == 0) {
            fileSendMenuItem.setEnabled(false);
            discutionCreateMenuItem.setEnabled(false);
        } else {
            fileSendMenuItem.setEnabled(true);
            discutionCreateMenuItem.setEnabled(true);
        }
        System.out.println("table click");
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        btAjouter.setText("Ajouter un contact");
        btAjouter.addActionListener(new ContactListPanel_btAgouter_actionAdapter(this));
        setBackground(Color.orange);
        discutionCreateMenuItem.setText("Créer une discussion");
        contactDeleteMenuItem.setText("Supprimer");
        fileSendMenuItem.setText("Envoyer un fichier");
        popupMenu.add(discutionCreateMenuItem);
        popupMenu.add(contactDeleteMenuItem);
        popupMenu.add(fileSendMenuItem);
        fileSendMenuItem.setIcon(new ImageIcon(MainFrame.class.getResource("Document.png")));
        discutionCreateMenuItem.setIcon(new ImageIcon(MainFrame.class.getResource("Discussion.png")));
        contactDeleteMenuItem.setIcon(new ImageIcon(MainFrame.class.getResource("delete.png")));
        fileSendMenuItem.addActionListener(new ContactListPanel_fileSendMenuItem_actionAdapter(this));
        discutionCreateMenuItem.addActionListener(new ContactListPanel_discutionCreateMenuItem_actionAdapter(this));
        contactDeleteMenuItem.addActionListener(new ContactListPanel_contactDeleteMenuItem_actionAdapter(this));
        table.addMouseListener(new PopupTableListener(table, popupMenu));
        table.setRowHeight(30);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        this.add(jScrollPane1, BorderLayout.CENTER);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(btAjouter, null);


        jScrollPane1.getViewport().add(table, null);
        table.getColumnModel().getColumn(0).setMaxWidth(ICON_COLUMN_WIDTH);
        table.getColumnModel().getColumn(0).setMinWidth(ICON_COLUMN_WIDTH);
        table.setDefaultRenderer(JLabel.class, new ContctListCellRenderer());
        table.setSelectionMode(table.getSelectionModel().SINGLE_SELECTION);
        table.addMouseListener(new ContactListPanel_table_actionAdapter(this));
        // table.addMouseListener();
        this.setOpaque(true);
    }

    public void addContact(int stat, OurString name) {
        model.addContact(stat, name);
        model.fireTableDataChanged();
    }

    public void deleteContact(int index) {
    }

    public void changeState(String pseudo, String state) {
        model.changeState(new OurString(pseudo), Integer.parseInt(state));
        model.fireTableDataChanged();
    }

    void btAjouter_actionPerformed(ActionEvent e) {
        Dialogue dialo = new Dialogue("", false);
        dialo.add(new AjouterPane(dialo));
        dialo.setSize(300, 200);
        dialo.setLocation(200, 200);
        dialo.setVisible(true);
    }

    void btSupprimer_actionPerformed(ActionEvent e) {
        model.removeContact(table.getSelectedRow());
        model.fireTableDataChanged();

    }

    void btCreateChatRoom_actionPerformed(ActionEvent e) {
        ChatPanel proom = new ChatPanel(model.getPseudo(table.getSelectedRow()), observer);
        observer.addChatPane(proom, model.getPseudo(table.getSelectedRow()));


    }

    void btFileSend_actionPerformed(ActionEvent e) {
        FileSendPane fileSendPane = new FileSendPane(model.getPseudo(table.getSelectedRow()));
        observer.addPane(fileSendPane, "Envoi de fichier à " + model.getPseudo(table.getSelectedRow()));
    }

    public void reponse(boolean b, String info) {
        if (b) {
            MainFrame.client.WriteMessage("ReponseAjouter" + "," + 1 + "," + info);
            StringTokenizer st1 = new StringTokenizer(info, ",");
            String id = st1.nextToken();
            String pseudo = st1.nextToken();
            observer.addContact(1, new OurString(pseudo));
            MainFrame.client.addConact(new Long(id), pseudo);
        } else {
            MainFrame.client.WriteMessage("ReponseAjouter" + "," + 0 + "," + info);
        }
    }
}

class ContactListPanel_btAgouter_actionAdapter
        implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_btAgouter_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btAjouter_actionPerformed(e);
    }
}

class ContactListPanel_btSupprimer_actionAdapter
        implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_btSupprimer_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btSupprimer_actionPerformed(e);
    }
}

class ContactListPanel_btCreateChatRoom_actionAdapter implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_btCreateChatRoom_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btCreateChatRoom_actionPerformed(e);
    }
}

class ContactListPanel_btFileSend_actionAdapter implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_btFileSend_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btFileSend_actionPerformed(e);
    }
}

class ContactListPanel_fileSendMenuItem_actionAdapter implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_fileSendMenuItem_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.fileSendMenuItem_actionPerformed(e);
    }
}

class ContactListPanel_contactDeleteMenuItem_actionAdapter implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_contactDeleteMenuItem_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.contactDeleteMenuItem_actionPerformed(e);
    }
}

class ContactListPanel_discutionCreateMenuItem_actionAdapter implements ActionListener {

    ContactListPanel adaptee;

    ContactListPanel_discutionCreateMenuItem_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.discutionCreateMenuItem_actionPerformed(e);
    }
}

class ContactListPanel_table_actionAdapter implements MouseListener {

    ContactListPanel adaptee;

    ContactListPanel_table_actionAdapter(ContactListPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.table_actionPerformed(e);
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.table_mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}