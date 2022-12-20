package client;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 * cette  classs est une thread cree pour recevoir des message du serveur
 *quelque soit un neveau message ou  un message consernont l'etas des client
 * du list contact  (conneté,deconnecté)
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Client implements ClientInterface {

    //public volatile static boolean notAuthenticated = true;
    static String serverHost = "localhost";
    public static String fileServerHost = "localhost";
    static int port = 1234;
    JTextArea Text = null;
    Vector v = null;
    JList list = null;
    private Socket socket;
    private String login;
    private int id;
    private Hashtable<Long, String> contactList = new Hashtable<Long, String>();
    private Hashtable<String, Long> pseudIdContactList = new Hashtable<String, Long>();
    public Hashtable<Long, FilePaneInterface> fileTable = new Hashtable<Long, FilePaneInterface>();
    private Hashtable<Long, Long> serialTable = new Hashtable<Long, Long>();
    long localSerial = 0;
    private BufferedReader din = null; // flux d'entr�e
    private PrintWriter dout = null; // flux de sortie
    //ClientInterface clientInterface;
// Constructor

    /*  public Client(ClientInterface clientInterface) {
    // this.clientInterface = clientInterface;
    }*/
    public boolean creeConnexion() {
        try {
            socket = new Socket(serverHost, port);
            System.out.println("connecté à " + socket);
            din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dout = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException ie) {
            System.out.println(ie + " connexion refusée ");
            return false;
        }
    }

   public long addFilePane(FilePaneInterface p) {
        fileTable.put(localSerial, p);
       localSerial++;
        return localSerial-1;
    }
public void addFielSerial(Long serial,Long localSerial){
    serialTable.put(serial, localSerial);
}
    public boolean authentifier(String login, String motpass) {
        String messag = "", a = "authentifier" + "," + login + "," + motpass;
        System.out.println(a);
        try {

            dout.println(a);
            try {
                din.readLine();
                messag = din.readLine();

            } catch (IOException ex) {
                System.out.println("IO exception ");
            }
        } catch (Exception e) {
            System.out.println(" exceptionexceptionexceptionexception ");
        }
        if ("ok".equals(messag)) {
            return true;
        } else {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(" exception io close de socket");
            }
            return false;
        }
    }

    public void register(String coordonnées) {
        String pseudo = "";
        String pass = "";
        StringTokenizer st1 = new StringTokenizer(coordonnées, ",");
        pseudo = st1.nextToken();
        pass = st1.nextToken();
        dout.println("Register" + "," + pseudo + "," + pass);
    }

    public String get() {
        String m = "";
        String erreur = "";
        m = ReadMessage();
        return m;
    }

    public String getlist() {
        String rep = "";

        return rep;
    }

    public void envoiyerMessage(String mesge) {
        String erreur = "erreur de envoi de message";
        String m = mesge;

    }

    public void fermer_connexion() {
        System.out.println("send fermer");
        String erreur = "erreur de fermeture";
        WriteMessage("fermer");
    }

    public void addConact(Long id, String pseudo) {
        contactList.put(id, pseudo);
        pseudIdContactList.put(pseudo, id);

    }

    public void setJlist(JList list, Vector v, JTextArea TArea) {
    }

    public void lancerThreadEcout() {
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void showlist() {
        Enumeration e = contactList.keys();
        while (e.hasMoreElements()) {
            System.out.println(contactList.get(e.nextElement()));
        }
    }
    /*
    public String chercherUnNeveaucontact(String mot) {
    String list = "";
    String erreur = "erreur de chercher";
    if (TentativeConnection == true) {
    this.CloseSoket();
    } else {
    this.creeConnexion();
    }

    System.out.println("list recu " + list);

    return list;
    }
     */

    public void WriteMessage(String mes) {
        dout.println(mes);
        System.out.println("message write");
    }

    public String ReadMessage() {
        String rep = "";
        try {
            rep = din.readLine();
        } catch (IOException ex) {
            System.out.println("exception: read ");
        }
        return rep;
    }

    public String getPseudo(Long id) {
        return contactList.get(id);
    }

    public Long getID(String pseudo) {
        return pseudIdContactList.get(pseudo);

    }

    public String getmonpseoudo() {
        return this.login;
    }

    public int getmonId() {
        return id;
    }

    public void setmonpseoudo(String login) {
        this.login = login;
    }

    public void setmonId(int id) {
        this.id = id;
    }

    public void CloseSoket() {
        try {

            din.close();
            dout.close();
            socket.close();

        } catch (IOException ex) {
        }
    }

    private static String IdentifieHost(InetAddress Host) {
// identification de Host
        String ipHost = Host.getHostAddress();
        String nomHost = Host.getHostName();
        String idHost;
        if (nomHost == null) {
            idHost = ipHost;
        } else {
            idHost = ipHost + "," + nomHost;
        }
        return idHost;
    }
}
