package GUI;

import client.FilePaneInterface;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;


/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 * @author non attribuable
 * @version 1.0
 */
public class FileSendPane extends JPanel implements FilePaneInterface {

    FileInputStream fin = null;
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel panPath = new JPanel();
    JButton btBrowse = new JButton();
    JTextField textPath = new JTextField();
    JPanel panCommande = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JButton btStart = new JButton();
    JButton btPause = new JButton();
    JButton btStop = new JButton();
    JPanel panProgress = new JPanel();
    JProgressBar jProgressBar1 = new JProgressBar();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border1;
    TitledBorder titledBorder1;
    JPanel panInfo = new JPanel();
    JLabel labFileName = new JLabel();
    JLabel labSpeed = new JLabel();
    JLabel labSize = new JLabel();
    JLabel labRemainingTime = new JLabel();
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    String pseudo = "";
    long taille = 0;
    String nom = "";

    public FileSendPane(String pseudo) {
        this.pseudo = pseudo;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(165, 163, 151));
        titledBorder1 = new TitledBorder(border1, "Avancement");
        this.setLayout(verticalFlowLayout1);
        panPath.setLayout(borderLayout2);
        btBrowse.setText("Parcourir");
        btBrowse.addActionListener(new FileSendPane_btBrowse_actionAdapter(this));

        textPath.setText("");

        btStart.setText("Démarrer");
        btStart.addActionListener(new FileSendPane_btStart_actionAdapter(this));
        btPause.setText("Pause");
        btPause.addActionListener(new FileSendPane_btPause_actionAdapter(this));
        btStop.setText("Arrêter");
        btStop.addActionListener(new FileSendPane_btStop_actionAdapter(this));
        panProgress.setLayout(borderLayout1);
        panProgress.setBorder(titledBorder1);
        labFileName.setRequestFocusEnabled(true);
        labFileName.setToolTipText("");
        labFileName.setText("Nom de fichier :");
        labSpeed.setText("Vitesse :");
        labSize.setToolTipText("");
        labSize.setText("Taille :");
        labRemainingTime.setText("Temps restant :");
        panInfo.setLayout(verticalFlowLayout2);
        this.add(panPath, null);
        panPath.add(textPath, BorderLayout.CENTER);
        panPath.add(btBrowse, BorderLayout.EAST);
        this.add(panCommande, null);
        panCommande.add(btStop, null);
        panCommande.add(btStart, null);
        panCommande.add(btPause, null);
        this.add(panProgress, null);
        panProgress.add(jProgressBar1, BorderLayout.CENTER);
        this.add(panInfo, null);
        panInfo.add(labFileName, null);
        panInfo.add(labSpeed, null);
        panInfo.add(labSize, null);
        panInfo.add(labRemainingTime, null);
    }

    void btBrowse_actionPerformed(ActionEvent e) {
        int res;
        JFileChooser fs = new JFileChooser();
        res = fs.showOpenDialog(this);
        if (res == JFileChooser.CANCEL_OPTION) {
        } else {
            if (res == JFileChooser.APPROVE_OPTION) {
                nom = fs.getSelectedFile().getName();
                taille = fs.getSelectedFile().length();
                textPath.setText(fs.getSelectedFile().getPath());
                labSize.setText("Taille: " + taille + " octet");
                labFileName.setText("Nom de fichier: " + nom);
                File file = new File(fs.getSelectedFile().getPath());
                try {
                    fin = new FileInputStream(file);
                // new FileSender(MainFrame.client.getSocket(), fin).execute();

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileSendPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
            }

        }
    }

    public void startTransfert(Long serial) {
        System.out.println("startSend " + serial);
        FileSender task = new FileSender(textPath.getText(), taille, serial);
        task.addPropertyChangeListener(
                new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {
                        // jProgressBar1.setValue(50);

                        if ("progress".equalsIgnoreCase(evt.getPropertyName())) {
                            labSpeed.setText("transféré: " + (Integer) evt.getNewValue() + "");
                            jProgressBar1.setValue((Integer) evt.getNewValue());
                        }
                    }
                });
        task.execute();
    }

    void btPause_actionPerformed(ActionEvent e) {
    }

    void btStart_actionPerformed(ActionEvent e) {
        MainFrame.client.WriteMessage("SendFile" + "," + MainFrame.client.getID(pseudo) + "," + MainFrame.client.addFilePane(this) + "," + nom + "," + taille);
    }

    void btStop_actionPerformed(ActionEvent e) {
    }
}
class FileSendPane_btBrowse_actionAdapter implements java.awt.event.ActionListener {

    FileSendPane adaptee;

    FileSendPane_btBrowse_actionAdapter(FileSendPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btBrowse_actionPerformed(e);
    }
}

class FileSendPane_btPause_actionAdapter implements java.awt.event.ActionListener {

    FileSendPane adaptee;

    FileSendPane_btPause_actionAdapter(FileSendPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btPause_actionPerformed(e);
    }
}

class FileSendPane_btStart_actionAdapter implements java.awt.event.ActionListener {

    FileSendPane adaptee;

    FileSendPane_btStart_actionAdapter(FileSendPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btStart_actionPerformed(e);
    }
}

class FileSendPane_btStop_actionAdapter implements java.awt.event.ActionListener {

    FileSendPane adaptee;

    FileSendPane_btStop_actionAdapter(FileSendPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btStop_actionPerformed(e);
    }
}
