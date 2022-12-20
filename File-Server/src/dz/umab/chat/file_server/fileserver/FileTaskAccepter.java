/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class FileTaskAccepter implements Callable {

    Socket socket;
    private BufferedReader inStream;

    public void init() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(12345);
            System.out.println("FileTaskAccepter ecoute ...");
        } catch (IOException ex) {
            Logger.getLogger(FileTaskAccepter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket = ss.accept();
            System.out.println("serveur chat est connecté " + socket);
        } catch (IOException ex) {
            System.out.println("erreur de connexion du serveur chat " + socket);
        }
        try {
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("le flux du socket avec le seveur chat est lié.");
        } catch (IOException ex) {
            System.out.println("le flux du socket avec le seveur chat n'a pas pu être lié.");
        }
    }

    public Object call() throws Exception {
        String serial = "";
        init();
        while (true) {
            serial = inStream.readLine();
            FileServerMain.table.put(new Long(serial), new QueueAndStatus());
        }
    }
}
