package dz.umab.chat.dskclient.client;

import dz.umab.chat.dskclient.GUI.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société : </p>
 *
 * @author non attribuable
 * @version 1.0
 */
public class ClientApp {

    static public ExecutorService worker = Executors.newFixedThreadPool(3);
    boolean packFrame = false;
    //Construire l'application

    public ClientApp() {
        MainFrame frame = new MainFrame();
        URL url = this.getClass().getResource("Green_Button.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        try {
            MediaTracker mt = new MediaTracker(frame);
            mt.addImage(image, 0);
            mt.waitForAll();
            frame.setIconImage(image);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Valider les cadres ayant des tailles pr�d�finies
        //Compacter les cadres ayant des infos de taille pr�f�r�es - ex. depuis leur disposition
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        //Centrer la fen�tre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width), 100);
        frame.setVisible(true);
    }
    //Méthode main

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ClientApp();

        try {
            Client.serverHost = args[0];
            Client.fileServerHost = args[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}