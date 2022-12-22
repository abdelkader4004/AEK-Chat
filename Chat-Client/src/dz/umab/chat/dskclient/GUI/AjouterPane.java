package dz.umab.chat.dskclient.GUI;

import com.borland.jbcl.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AjouterPane extends Panneau {
    JPanel panEntete = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JLabel labmes = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel panSaisi = new JPanel();
    JTextField textNom = new JTextField();
    JPanel panCommand = new JPanel();
    JButton btOK = new JButton();
    JButton btCancel = new JButton();
    Dialogue dialogue;

    public AjouterPane(Dialogue dialogue) {
        this.dialogue = dialogue;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        this.setLayout(verticalFlowLayout1);
        panEntete.setForeground(Color.red);
        panEntete.setOpaque(false);
        panEntete.setMinimumSize(new Dimension(10, 10));

        panEntete.setPreferredSize(new Dimension(390, 50));
        panEntete.setRequestFocusEnabled(true);
        panEntete.setLayout(flowLayout1);
        labmes.setToolTipText("");
        labmes.setText("Veillez saisir le nom");
        panSaisi.setPreferredSize(new Dimension(390, 50));
        panSaisi.setRequestFocusEnabled(true);
        panSaisi.setOpaque(false);

        textNom.setPreferredSize(new Dimension(200, 20));
        textNom.setRequestFocusEnabled(true);
        textNom.setSelectedTextColor(Color.white);
        textNom.setText("");
        textNom.addActionListener(new AjouterPane_textNom_actionAdapter(this));
        btOK.setText("OK");
        btOK.addActionListener(new AjouterPane_btOK_actionAdapter(this));
        btCancel.setText("Annuler");
        btCancel.addActionListener(new AjouterPane_btCancel_actionAdapter(this));
        this.setOpaque(true);
        this.add(panEntete, null);
        this.add(panSaisi, null);
        this.add(panCommand, null);
        panSaisi.add(textNom, null);
        panEntete.add(labmes, null);
        panCommand.add(btOK, null);
        panCommand.add(btCancel, null);
        panCommand.setOpaque(false);
    }

    void textNom_actionPerformed(ActionEvent e) {

    }

    void btOK_actionPerformed(ActionEvent e) {
        MainFrame.client.WriteMessage("Ajouter" + "," + textNom.getText());
        dialogue.setVisible(false);
    }

    void btCancel_actionPerformed(ActionEvent e) {
        dialogue.setVisible(false);
    }
}

class AjouterPane_textNom_actionAdapter implements ActionListener {
    AjouterPane adaptee;

    AjouterPane_textNom_actionAdapter(AjouterPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textNom_actionPerformed(e);
    }
}

class AjouterPane_btOK_actionAdapter implements ActionListener {
    AjouterPane adaptee;

    AjouterPane_btOK_actionAdapter(AjouterPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btOK_actionPerformed(e);
    }
}

class AjouterPane_btCancel_actionAdapter implements ActionListener {
    AjouterPane adaptee;

    AjouterPane_btCancel_actionAdapter(AjouterPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btCancel_actionPerformed(e);
    }
}
