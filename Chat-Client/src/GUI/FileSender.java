/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import client.Client;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author user
 */
public class FileSender extends SwingWorker {
    BufferedOutputStream sOut = null;
    FileInputStream in = null;
    private Long fileSize;
    private String filePath;
    Socket socket = null;
    long serial = 0;
    public FileSender(String filePath, Long fileSize, long serial) {
        this.fileSize = new Long(fileSize);
        this.filePath = filePath;
        this.serial = serial;
    }
    private void init() {
        File file = new File(filePath);
        try {
            socket = new Socket(Client.fileServerHost, 1999);
        } catch (UnknownHostException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sOut = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        int i = 0;
        boolean fin = false;
        int nbreBytesTransferes = 0;
        init();
        sOut.write(("Send000,4\n").getBytes());
        //sOut.flush();
        byte[] b = new byte[10000];
        while (!fin) {
            i = in.read(b);
            if (i > 0) {
                sOut.write(b, 0, i);
                sOut.flush();
                nbreBytesTransferes += i;
                setProgress((int) (nbreBytesTransferes * 100 / fileSize));
            } else {
                fin = true;
            }
        }
        System.out.println("nbreBytesTransferes= " +nbreBytesTransferes);
        sOut.close();
        in.close();
        socket.close();        
        return null;
    }

    @Override
    public void done() {
        System.out.println("envoi de fichier terminé");
    }
}
