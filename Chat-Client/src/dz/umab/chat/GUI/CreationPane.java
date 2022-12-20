/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
package GUI;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import client.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 * @author non attribuable
 * @version 1.0
 */
//textPrenom textNom PasswordField PasswordFieldConfirm textPseudo
public class CreationPane extends Panneau {

    BorderLayout borderLayout1 = new BorderLayout(20, 20);
    JPanel lesinfo = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JLabel labMotDePass = new JLabel();
    JPanel panemotpass = new JPanel();
    JLabel labPrenom = new JLabel();
    JTextField textPrenom = new JTextField();
    JPanel panePrenom = new JPanel();
    JLabel labNom = new JLabel();
    JTextField textNom = new JTextField();
    JPanel paneNom = new JPanel();
    JLabel labComfirmer = new JLabel();
    JPanel panConfirm = new JPanel();
    JPasswordField PasswordField = new JPasswordField();
    JPasswordField PasswordFieldConfirm = new JPasswordField();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    GridLayout gridLayout3 = new GridLayout();
    GridLayout gridLayout4 = new GridLayout();
    JPanel panEntete = new JPanel();
    JLabel labEntete = new JLabel();
    JPanel panValider = new JPanel();
    JButton btValider = new JButton();
    ObserverInterface observer;
    JButton btAnnuler = new JButton();
    JTextField textPseudo = new JTextField();
    JLabel labPseudo = new JLabel();
    JPanel panePseudo = new JPanel();
    GridLayout gridLayout5 = new GridLayout();
    JPanel reponse = new JPanel();
    JLabel message = new JLabel();

    public CreationPane(ObserverInterface observer) {
        try {
            this.observer = observer;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void btAnnuler_actionPerformed(ActionEvent e) {
        observer.removePaneCreation();

        observer.addConnectionPane();
    //   observer.
    }

    void jbInit() throws Exception {
        this.setOpaque(false);
        this.setLayout(borderLayout1);
        lesinfo.setLayout(verticalFlowLayout1);
        labMotDePass.setFont(new java.awt.Font("Arial", 1, 14));
        labMotDePass.setForeground(Color.darkGray);
        labMotDePass.setText("Mot de pass");
        panemotpass.setLayout(gridLayout3);
        labPrenom.setFont(new java.awt.Font("Arial", 1, 14));
        labPrenom.setForeground(Color.darkGray);
        labPrenom.setText("Prénom");
        textPrenom.setRequestFocusEnabled(true);

        textPrenom.addActionListener(new CreationPane_textPrenom_actionAdapter(this));
        textPrenom.setPreferredSize(new Dimension(300, 25));
        textPrenom.setOpaque(true);
        panePrenom.setLayout(gridLayout2);
        labNom.setFont(new java.awt.Font("Arial", 1, 14));
        labNom.setForeground(Color.darkGray);
        labNom.setText("Nom");
        textNom.setRequestFocusEnabled(true);

        textNom.setPreferredSize(new Dimension(300, 25));
        textNom.setEnabled(true);
        textNom.setOpaque(true);
        paneNom.setLayout(gridLayout1);
        labComfirmer.setFont(new java.awt.Font("Arial", 1, 14));
        labComfirmer.setForeground(Color.darkGray);
        labComfirmer.setText("Comfirmer le mot de pass");
        panConfirm.setLayout(gridLayout4);
        PasswordField.setPreferredSize(new Dimension(300, 25));

        PasswordFieldConfirm.setPreferredSize(new Dimension(300, 25));

        labEntete.setIcon(new ImageIcon(CreationPane.class.getResource("kopete.png")));
        labEntete.setText("");
        // panEntete.setPreferredSize(new Dimension(40, 50));
        btValider.setText("Valider");
        btAnnuler.setText("Annuler");
        btValider.addActionListener(new CreationPane_btValider_actionAdapter(this));
        btAnnuler.addActionListener(new CreationPane_btAnnuler_actionAdapter(this));
        textPseudo.setOpaque(true);
        textPseudo.setPreferredSize(new Dimension(300, 25));


        textPseudo.setRequestFocusEnabled(true);
        labPseudo.setText("Pseudo");
        labPseudo.setForeground(Color.darkGray);
        labPseudo.setFont(new java.awt.Font("Arial", 1, 14));
        panePseudo.setLayout(gridLayout5);

        reponse.setAlignmentX((float) 0.5);

        message.setBackground(Color.white);
        message.setFont(new java.awt.Font("Dialog", 1, 16));
        message.setForeground(Color.WHITE);
        message.setOpaque(false);

        panConfirm.add(labComfirmer, null);
        panConfirm.setOpaque(false);
        panConfirm.add(PasswordFieldConfirm, null);
        lesinfo.add(reponse, null);
        reponse.add(message, null);
        reponse.setOpaque(false);
        this.add(panEntete, BorderLayout.NORTH);
        panEntete.add(labEntete, null);
        panEntete.setOpaque(false);
        this.add(panValider, BorderLayout.SOUTH);
        panValider.add(btValider, null);
        panValider.add(btAnnuler, null);
        panValider.setOpaque(false);
        this.add(lesinfo, BorderLayout.CENTER);
        panePrenom.add(labPrenom, null);
        panePrenom.setOpaque(false);
        panePrenom.add(textPrenom, null);
        paneNom.add(labNom, null);
        paneNom.setOpaque(false);
        paneNom.add(textNom, null);
        panePseudo.add(labPseudo, null);
        panePseudo.add(textPseudo, null);
        panePseudo.setOpaque(false);
        lesinfo.add(paneNom, null);
        lesinfo.add(panePrenom, null);
        lesinfo.add(panePseudo, null);
        lesinfo.add(panemotpass, null);
        panemotpass.add(labMotDePass, null);
        panemotpass.setOpaque(false);
        panemotpass.add(PasswordField, null);
        lesinfo.add(panConfirm, null);
        lesinfo.setOpaque(false);
        message.setText("Veuillez saisir vos coordonnées");
    }

    void textPrenom_actionPerformed(ActionEvent e) {
    }

    void btValider_actionPerformed(ActionEvent e) {
        String coordonnes = "";
        System.out.println("pseudo " + textPseudo.getText());

        if (new OurString(String.valueOf(PasswordField.getPassword())).equals(new OurString(String.valueOf(PasswordFieldConfirm.getPassword())))) {
            observer.startAnimationPane();
            Client client = new Client();
            observer.setClient(client);
            coordonnes = textPseudo.getText() + "," + String.valueOf(PasswordField.getPassword());
            CreateTask createTask = new CreateTask(coordonnes, observer);
            ClientApp.worker.submit(createTask);            //MainFrame.client.Cree(coordonnes);
        } else {
            labEntete.setText("mot de pass erreur");
        //message.setText("Veuillez resaisir vos coordonnées");
        }
    }
}

class CreationPane_textPrenom_actionAdapter implements java.awt.event.ActionListener {

    CreationPane adaptee;

    CreationPane_textPrenom_actionAdapter(CreationPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.textPrenom_actionPerformed(e);
    }
}

class CreationPane_btValider_actionAdapter implements java.awt.event.ActionListener {

    CreationPane adaptee;

    CreationPane_btValider_actionAdapter(CreationPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btValider_actionPerformed(e);
    }
}

class CreationPane_btAnnuler_actionAdapter implements java.awt.event.ActionListener {

    CreationPane adaptee;

    CreationPane_btAnnuler_actionAdapter(CreationPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btAnnuler_actionPerformed(e);
    }
}
