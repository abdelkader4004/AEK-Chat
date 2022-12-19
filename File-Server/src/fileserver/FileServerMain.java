/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class FileServerMain {

    static ServerSocket ss;
    private static final int THREAD_COUNT = 6;
    static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    static Hashtable<Long, QueueAndStatus> table = new Hashtable<Long, QueueAndStatus>();
    static FileTaskAccepter fileTaskAccepter = new FileTaskAccepter();
    

    public static void main(String[] arg) {
      threadPool.submit(fileTaskAccepter);
      listen();
    }

   static void listen() {
        try {
            ss = new ServerSocket(1999);
            System.out.println("fileServer ecoute ...");
        } catch (IOException ex) {
            Logger.getLogger(FileServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                Socket sclient = ss.accept();
                FileServerTask task = new FileServerTask(sclient);
                threadPool.submit(task);
                System.out.println("passer submit ");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
