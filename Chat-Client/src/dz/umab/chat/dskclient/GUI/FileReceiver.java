/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.umab.chat.dskclient.GUI;

import dz.umab.chat.dskclient.client.Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author user
 */
public class FileReceiver extends SwingWorker {

    //BufferedWriter sOut = null;
    BufferedInputStream sIn = null;
    FileOutputStream fOut = null;
    long fileSize;
    String fileName;
    long serial = 0;
    private PrintWriter outStream;

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
        byte[] b = new byte[10000];
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
        System.out.println("nbreBytesReçus " + nbreBytesTransferes);
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
