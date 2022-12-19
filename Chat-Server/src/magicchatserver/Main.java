/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicchatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JOptionPane;

/**
 *
 * @author el-magic
 */
public class Main {

    static final int THREAD_COUNT = 10;
    /**
     * HashTable that contains Long key representing the UID
     * and a value that is the corresponding clientTask.
     */
    static ConcurrentHashMap<Long, ClientTask> clientsTable = new ConcurrentHashMap<Long, ClientTask>();
    static ExecutorService threadPool = Executors.newFixedThreadPool(Main.THREAD_COUNT);
    static BlockingQueue<JDBCConnection> dataBaseConnexionPool;
    static PrintWriter fileServerOut = null;
    static String dbHost = "localhost";
    static String fileServerHost = "localhost";

    /**
     * used to init  database connections.
     */
    public static void initDBConnections() {
        try {
            Socket socket = new Socket(fileServerHost, 12345);
            fileServerOut = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException ex) {
            System.out.println("Serveur de fichiers introuvable");
        } catch (IOException ex) {
            System.out.println("IO exception de serv fichier");
        }
        try {
            dataBaseConnexionPool = new LinkedBlockingQueue<JDBCConnection>(5);
            JDBCConnection jdbc = new JDBCConnection();
            jdbc.connect();
            Main.dataBaseConnexionPool.put(jdbc);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, " erreur blockingqueue put", "erreur", 1);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            dbHost = args[0];
            fileServerHost = args[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.initDBConnections();
        threadPool.submit(new Dispatcher());
    }
}
