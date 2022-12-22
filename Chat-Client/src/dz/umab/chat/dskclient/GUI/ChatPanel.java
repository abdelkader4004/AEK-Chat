package dz.umab.chat.dskclient.GUI;

import dz.umab.chat.dskclient.client.ObserverInterface;
import com.borland.jbcl.layout.VerticalFlowLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 *
 * @author non attribuable
 * @version 1.0
 */
public class ChatPanel extends JPanel {
    JScrollPane scrollPane = new JScrollPane();
    JTextField textSend = new JTextField();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel panEdit = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton btSend = new JButton();
    JPanel panDialogSequence = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    Border border1;
    TitledBorder titledBorder1;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    String pseudo = "";
    ObserverInterface observer;

    public ChatPanel(String pseudo, ObserverInterface observer) {
        this.pseudo = pseudo;
        this.observer = observer;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white,
                new Color(167, 167, 167));
        titledBorder1 = new TitledBorder(border1, "Message à envoyer: ");
        this.setLayout(borderLayout1);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        textSend.setText("");
        textSend.addActionListener(new ChatPanel_textSend_actionAdapter(this));
        panEdit.setLayout(borderLayout2);
        btSend.setText("Envoyer");
        btSend.addActionListener(new ChatPanel_btSend_actionAdapter(this));
        panDialogSequence.setLayout(verticalFlowLayout1);
        panEdit.setBorder(titledBorder1);
        jPanel1.setLayout(borderLayout3);
        this.add(panEdit, BorderLayout.SOUTH);
        panEdit.add(textSend, BorderLayout.CENTER);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getViewport().add(panDialogSequence, null);
        panEdit.add(btSend, BorderLayout.EAST);
    }

    void btSend_actionPerformed(ActionEvent e) {
        if (!textSend.getText().equals("")) {
            try {
                MainFrame.client.WriteMessage("SendIM" + "," + MainFrame.client.getID(pseudo) + "," + textSend.getText());
                System.out.println("btSend_actionPerformed" + "SendIM" + "," + pseudo + "," + textSend.getText());
                JPanel p = new messagePane(new ImageIcon(ContactListTableModel.class.getResource("User.png")),
                        "Je dit:", textSend.getText(), new ImageIcon(ContactListTableModel.class.getResource("User.png")));
                panDialogSequence.add(p);
                textSend.setText("");
                scrollPane.validate();
                panDialogSequence.scrollRectToVisible(new Rectangle(0, panDialogSequence.getHeight() - 1, panDialogSequence.getWidth(), 1));

            } catch (Exception ex) {
            }
        }
    }

    public void receiveMessage(String mess, String Sender) {
        try {

            JPanel p = new messagePane(new ImageIcon(ContactListTableModel.class.getResource("User.png")),
                    Sender + " dit:", mess, new ImageIcon(ContactListTableModel.class.getResource("User.png")));
            panDialogSequence.add(p);
            scrollPane.validate();
            panDialogSequence.scrollRectToVisible(new Rectangle(0, panDialogSequence.getHeight() - 1, panDialogSequence.getWidth(), 1));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void textSend_actionPerformed(ActionEvent e) {
        btSend_actionPerformed(e);

    }
}

class ChatPanel_btSend_actionAdapter
        implements ActionListener {

    ChatPanel adaptee;

    ChatPanel_btSend_actionAdapter(ChatPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btSend_actionPerformed(e);
    }
}

class ChatPanel_textSend_actionAdapter implements ActionListener {

    ChatPanel adaptee;

    ChatPanel_textSend_actionAdapter(ChatPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textSend_actionPerformed(e);
    }
}
