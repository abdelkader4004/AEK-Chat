/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import client.Client;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.SwingWorker;

/**
 *
 * @author user
 */
public class FileReceiver extends SwingWorker {

    //BufferedWriter sOut = null;
    BufferedInputStream sIn = null;
    FileOutputStream fOut = null;
    private PrintWriter outStream;
    long fileSize;
    String fileName;
    long serial = 0;

    public FileReceiver(String fileName, String fileSize, long serial) {
        this.fileSize = new Long(fileSize);
        this.fileName = fileName;
        this.serial = serial;
    }

    private void init() {
        File file = new File(fileName);
        Socket socket = null;
        try {
            socket = new Socket(Client.fileServerHost, 1999);
            fOut = new FileOutputStream(file);
            sIn = new BufferedInputStream(socket.getInputStream());
            outStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        byte[] b=new byte[10000];
        int i = 0;
        int nbreBytesTransferes = 0;
        init();
        System.out.println("serial " + serial);
        outStream.println("Receive,4\n");
        while (fileSize > nbreBytesTransferes) {
           
            i = sIn.read(b);
            if (i >= 0) {
                nbreBytesTransferes += i;
                setProgress((int) (nbreBytesTransferes * 100 / fileSize));
                fOut.write(b, 0, i);
            }
        }
         System.out.println("nbreBytesReçus "+nbreBytesTransferes);
         fOut.flush();
        fOut.close();
        sIn.close();
        return null;
    }

    @Override
    public void done() {
        System.out.println("reception de fichier terminée");
    }
}
