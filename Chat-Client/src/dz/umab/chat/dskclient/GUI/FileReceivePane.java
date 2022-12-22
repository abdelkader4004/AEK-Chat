package dz.umab.chat.dskclient.GUI;

import dz.umab.chat.dskclient.client.FilePaneInterface;
import dz.umab.chat.dskclient.client.ObserverInterface;
import com.borland.jbcl.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.util.StringTokenizer;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 *
 * @author non attribuable
 * @version 1.0
 */
public class FileReceivePane extends JPanel implements BreakingEventWindowInterface, FilePaneInterface {

    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    JTextField textPath = new JTextField();
    JLabel labSize = new JLabel();
    JLabel labFileName = new JLabel();
    JPanel panPath = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel labRemainingTime = new JLabel();
    JPanel panCommande = new JPanel();
    JLabel labSpeed = new JLabel();
    JButton btStop = new JButton();
    JPanel panProgress = new JPanel();
    JProgressBar jProgressBar1 = new JProgressBar();
    JPanel panInfo = new JPanel();
    JButton btBrowse = new JButton();
    JButton btPause = new JButton();
    BorderLayout borderLayout3 = new BorderLayout();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    ObserverInterface observer;
    String fileSize = "", fileName = "";
    FileOutputStream fout = null;

    public FileReceivePane(ObserverInterface observer) {
        this.observer = observer;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        panPath.setLayout(borderLayout2);
        labFileName.setRequestFocusEnabled(true);
        labFileName.setToolTipText("");
        labFileName.setText("Nom de fichier :");
        labSize.setToolTipText("");
        labSize.setText("Taille :");
        textPath.setText("");
        this.setLayout(verticalFlowLayout1);
        labRemainingTime.setText("Temps restant :");

        labSpeed.setText("Vitesse :");
        btStop.setText("Arr�ter");
        btStop.addActionListener(new FileReceivePane_btStop_actionAdapter(this));
        panProgress.setLayout(borderLayout3);
        panInfo.setLayout(verticalFlowLayout2);
        btBrowse.setText("Parcourir");
        btBrowse.addActionListener(new FileReceivePane_btBrowse_actionAdapter(this));
        btPause.setText("Pause");
        btPause.addActionListener(new FileReceivePane_btPause_actionAdapter(this));
        panPath.add(textPath, BorderLayout.CENTER);
        panPath.add(btBrowse, BorderLayout.EAST);
        panCommande.add(btStop, null);
        this.add(panPath, null);
        panCommande.add(btStop, null);
        this.add(panCommande, null);
        panCommande.add(btPause, null);
        this.add(panProgress, null);
        panProgress.add(jProgressBar1, BorderLayout.CENTER);
        this.add(panInfo, null);
        panInfo.add(labFileName, null);
        panInfo.add(labSpeed, null);
        panInfo.add(labSize, null);
        panInfo.add(labRemainingTime, null);

    }

    void btStop_actionPerformed(ActionEvent e) {
    }

    void btStart_actionPerformed(ActionEvent e) {
    }

    void btPause_actionPerformed(ActionEvent e) {
    }

    void btBrowse_actionPerformed(ActionEvent e) {
    }

    public void reponse(boolean b, String info) {

        StringTokenizer st1 = new StringTokenizer(info, ",");

        fileName = st1.nextToken();
        fileSize = st1.nextToken();
        labFileName.setText("Nom: " + fileName);
        labSize.setText("Taille: " + fileSize);
        String remoteUID = st1.nextToken();
        String remoteSerial = st1.nextToken();
        if (b) {
            //JDirectoryChooser chooser = new JDirectoryChooser();
            // int x = chooser.showOpenDialog(this);
            //   if (x == JFileChooser.APPROVE_OPTION) {
            //textPath.setText(chooser.getSelectedFile().getAbsolutePath());
            //  }

            //   File file = new File(fileName);
          /*  try {
            //     fout = new FileOutputStream(file);
            // new FileReceiver(MainFrame.client.getSocket(), fout, new Long(fileSize)).execute();
            } catch (FileNotFoundException ex) {
            System.out.println("exception FileNotFoundException");
            }*/
            Long localSerial = MainFrame.client.addFilePane(this);
            MainFrame.client.WriteMessage("RepenseSendFile" + "," + 1 + "," + remoteUID + "," + remoteSerial + "," + localSerial);
            observer.addPane(this, info);


        } else {

            MainFrame.client.WriteMessage("RepenseSendFile" + "," + -1 + "," + remoteUID + "," + remoteSerial);
        }

    }

    public void startTransfert(Long serial) {
        System.out.println("startTransfert " + serial);


        FileReceiver task = new FileReceiver(fileName, fileSize, serial);
        task.addPropertyChangeListener(
                new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {
                        // jProgressBar1.setValue(50);

                        if ("progress".equalsIgnoreCase(evt.getPropertyName())) {

                            jProgressBar1.setValue((Integer) evt.getNewValue());
                            labSpeed.setText("transféré: " + (Integer) evt.getNewValue() + "");
                        }
                    }
                });
        task.execute();
    }
}

class FileReceivePane_btStop_actionAdapter implements ActionListener {

    FileReceivePane adaptee;

    FileReceivePane_btStop_actionAdapter(FileReceivePane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btStop_actionPerformed(e);
    }
}

class FileReceivePane_btPause_actionAdapter implements ActionListener {

    FileReceivePane adaptee;

    FileReceivePane_btPause_actionAdapter(FileReceivePane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btPause_actionPerformed(e);
    }
}

class FileReceivePane_btBrowse_actionAdapter implements ActionListener {

    FileReceivePane adaptee;

    FileReceivePane_btBrowse_actionAdapter(FileReceivePane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btBrowse_actionPerformed(e);
    }
}
