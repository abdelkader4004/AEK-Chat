/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicchatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author el-magic
 */
public class Dispatcher implements Runnable {

    private volatile boolean continu = true;
    ServerSocket acceptingSocket;
    Socket sclient;

    public Dispatcher() {
        try {
            acceptingSocket = new ServerSocket(1234);
            System.out.println("Le serveur ecoute ...");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, " erreur io sur bind", "erreur", 1);
        }
    }

    public void run() {
        while (continu) {

            try {
                sclient = acceptingSocket.accept();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, " erreur accept io", "erreur", 1);
            }
            ClientTask task = new ClientTask(sclient);
            Main.threadPool.submit(task);
            System.out.println("passer submit ");
        }





    }//run
}
