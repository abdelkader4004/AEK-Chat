/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package magicchatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author el-magic
 */
public class ClientTask<Integer> implements Callable {

    JDBCConnection jdbc;
    private Socket socket;
    private PrintWriter outStream;
    private BufferedReader inStream;
    private long uid;
    private Vector<Long> contactsUID = new Vector<Long>();
    private Hashtable<Long, ClientTask> tableConnectedContacts = new Hashtable<Long, ClientTask>();
    private int presenceState;
    boolean continu = true;
    String message = "";
    String pseudo = "";

    public ClientTask(Socket socket) {
        this.socket = socket;
        try {
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            outStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ClientTask.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * This method test either the given password and login are correct or not.
     * @param givenPass
     * @param givenLogin
     * @return
     */
    public boolean authenticate(String messag) {
        // String messag = "";
        String login = "";
        String password = "";
        boolean isOK = false;
        outStream.println("Hello");
        /*  try {
        //messag = inStream.readLine();
        //System.out.println("message recu"+message);
        } catch (IOException ex) {
        //System.out.println(" exeption "+message);
        //Logger.getLogger(ClientTask.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            StringTokenizer StrTok = new StringTokenizer(messag, ",");
            //
            StrTok.nextToken();
            login = StrTok.nextToken();
            password = StrTok.nextToken();

            System.out.println("login= " + login + " mot= " + password);
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("Erreur string tokenizer authentificate");
            return false;
        }
        try {
            try {
                jdbc = Main.dataBaseConnexionPool.take();
                isOK = jdbc.authenticate(login, password);
                if (isOK) {
                    this.pseudo = login;
                    uid = Long.parseLong(jdbc.getUID(login));
                    try {
                        Main.clientsTable.put(new Long(uid), this);
                    } catch (Exception e) {
                        System.out.println("erreur insert hashMap");
                        e.printStackTrace();
                    }
                    System.out.println("UID=" + uid);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " erreur SQL", "erreur", 1);

            }
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, " erreur blockingqueue take", "erreur", 1);

        }
        try {
            Main.dataBaseConnexionPool.put(jdbc);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, " erreur blockingqueue put fin", "erreur", 1);
        }
        return isOK;
    }

    /**
     * Informe this client that the caller client has change his presence state.
     */
    public void changePresenceState(int newPresenseState) {
    }

    private void contactDelete(String message) {
        StringTokenizer StrTok = new StringTokenizer(message, ",");
            StrTok.nextToken();
            long toDeleteUID = new Long(StrTok.nextToken());
        try {
            jdbc = Main.dataBaseConnexionPool.take();
        } catch (Exception e) {
        }
       jdbc.contactDelete(toDeleteUID,uid);
        try {
            Main.dataBaseConnexionPool.put(jdbc);
        } catch (Exception e) {
        }
    }

    private void sendFileReply(String message) {
        System.out.println("entrer ");
        try {
            StringTokenizer StrTok = new StringTokenizer(message, ",");
            StrTok.nextToken();
            String rep = StrTok.nextToken();
            ClientTask ct = null;
            String id = StrTok.nextToken();
            String senderSerial = StrTok.nextToken();
            String receiverSerial = StrTok.nextToken();
            ct = Main.clientsTable.get(new Long(id));
            System.out.println("rep = " + rep);
            System.out.println(" id= " + id);
            if (ct != null) {
                System.out.println("rep file ct n'est pas nulle");
                if ("-1".equals(rep)) {
                    ct.outStream.println("ReponseSendFile" + "," + "-1" + "," + uid + "," + senderSerial);
                } else {
                    if ("1".equals(rep)) {
                        ct.outStream.println("ReponseSendFile" + "," + "1" + "," + uid + "," + receiverSerial + "," + 4);
                        outStream.println("Serial" + "," + receiverSerial + "," + 4);
                        Main.fileServerOut.println(4);
                    }
                }
            } else {
                System.out.println("rep  file ct est  nulle");
            }
        } catch (Exception e) {
            System.out.println("exception rep " + e);
        }
    }

    private void contactAddReply(String message) {
        try {
            StringTokenizer StrTok = new StringTokenizer(message, ",");
            StrTok.nextToken();
            String rep = StrTok.nextToken();
            ClientTask ct = null;
            String id = StrTok.nextToken();
            ct = Main.clientsTable.get(new Long(id));
            if (ct != null) {
                if ("1".equals(rep)) {
                    ct.outStream.println("ReponseAjouter" + "," + 1 + "," + uid + "," + pseudo);
                    try {
                        jdbc = Main.dataBaseConnexionPool.take();
                    } catch (Exception e) {
                    }
                    jdbc.addContact(uid, new Long(id));
                    try {
                        Main.dataBaseConnexionPool.put(jdbc);
                    } catch (Exception e) {
                    }
                    ct.tableConnectedContacts.put(uid, this);
                    ct.contactsUID.add(uid);
                    this.tableConnectedContacts.put(new Long(id), ct);
                    this.contactsUID.add(new Long(id));
                } else {
                    ct.outStream.println("ReponseAjouter" + "," + -2 + "," + "x" + "," + pseudo);
                }
            } else {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, " erreur rep ag ", "erreur", 1);
        }
    }

    private boolean acountCreate(String messag) {
        String login = "";
        String password = "";
        boolean isOK = false;
        StringTokenizer StrTok = new StringTokenizer(messag, ",");
        StrTok.nextToken();
        login = StrTok.nextToken();
        password = StrTok.nextToken();
        try {
            jdbc = Main.dataBaseConnexionPool.take();
        } catch (Exception e) {
        }
        isOK = jdbc.Insert(login, password);
        try {
            Main.dataBaseConnexionPool.put(jdbc);
        } catch (Exception e) {
        }
        return isOK;
    }

    private void sendContactsList(String contactsList) {
        outStream.println(contactsList);
    }

    private void fillAndSendContactsList() {
        Long l;
        ClientTask ct;
        String contactsList = "", contactsStat = "";
        StringTokenizer StrTok1, StrTok2;
        try {
            jdbc = Main.dataBaseConnexionPool.take();
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException");
        }
        try {
            contactsList = jdbc.getlist(uid);
            try {
                Main.dataBaseConnexionPool.put(jdbc);
            } catch (InterruptedException ie) {
                System.out.println("InterruptedException");
            }
            // envoyer les la list des contact
            sendContactsList(contactsList);
            //remplire la list
            StrTok1 = new StringTokenizer(contactsList, ":");
            while (StrTok1.hasMoreElements()) {
                StrTok2 = new StringTokenizer(StrTok1.nextToken(), ",");
                contactsUID.add(Long.parseLong(StrTok2.nextToken()));
            }
            Iterator it = contactsUID.iterator();
            contactsStat = "";
            while (it.hasNext()) {
                l = (Long) it.next();
                //ct = null;
                ct = Main.clientsTable.get(l);
                if (ct != null) {//s'il est connecté
                    //ajouter l'user contact  dans la hashtable de clientTask courant
                    tableConnectedContacts.put(l, ct);
                    //ajouter lui meme   dans la hashtable de clientTask de user contact
                    ct.tableConnectedContacts.put(uid, this);
                    // envoyer au autre que j'ai connecté
                    ct.outStream.println("change" + "," + uid + "," + "1");
                    // remplire les list des client connecter.ct.outStream.println("connect"+","+uid);
                    contactsStat = contactsStat + "1" + "," + l + ":";
                }
            }
            if (contactsStat != null) {
                //contactsStat = contactsStat.substring(0, contactsStat.length() - 1);
                this.outStream.println("etat" + ":" + contactsStat);
            } else {
                this.outStream.println("etat" + ":" + "vide");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("String tokenizer 2   ");
        }
    }

    /**
     *
     */
    public void disconnect() throws Exception {
        Iterator it = tableConnectedContacts.keySet().iterator();
        String contactsStat = "";
        Long l;
        ClientTask ct;
        Main.clientsTable.remove(uid);
        System.out.println("je suis present dans pricip == " + Main.clientsTable.contains(uid));
        while (it.hasNext()) {
            l = (Long) it.next();

            ct = tableConnectedContacts.get(l);
            ct.outStream.println("change" + "," + uid + "," + "0");
            ct.deleteMe(uid);
            System.out.println("je suis present == " + uid + " " + ct.tableConnectedContacts.contains(uid));
        }/*
    Enumeration e = Main.clientsTable.keys();
    while (e.hasMoreElements()) {
    System.out.println("present uid== " + e.nextElement());
    }*/
    }

    public void deleteMe(long id) {
        tableConnectedContacts.remove(id);

    }

    /**
     *
     * @return
     * @throws java.lang.Exception
     */
    public Integer call() throws Exception {

        Long l;
        /**
         * authenticate user before chat
         */
        //---------------------------------------------------
        System.out.println(socket);
        boolean res = false;
        String messag;
        while (!res) {
            messag = inStream.readLine();
            System.out.println("auth crea" + message);
            if (messag == null) {
                res = true;
                continu = false;
            } else {
                if (messag.startsWith("authentifier")) {
                    res = authenticate(messag);
                    System.out.println("authenticte= " + res);

                } else {
                    if (messag.startsWith("Register")) {
                       System.out.println(message);
                        res = acountCreate(messag);
                    }
                }
                if (res) {

                    outStream.println("ok");
                } else {
                    outStream.println("false");
                }
            }
        }
        //---------------------------------------------------
        fillAndSendContactsList();
        //---------------------------------------------------
        while (continu) {
            try {
                message = inStream.readLine();
                if (message == null) {//si aucun message n'a était lu
                    continu = false;
                } else {//si un message était lu
                    System.out.println(message);
                    if (message.startsWith("SendIM")) {//1
                        sendIM(message);

                    } else {
                        if (message.startsWith("Ajouter")) {//2
                            addContact(message);

                        } else {
                            if (message.startsWith("ReponseAjouter")) {//3
                                contactAddReply(message);
                            } else {
                                if (message.startsWith("SendFile")) {//4
                                    SendFile(message);
                                } else {
                                    if (message.startsWith("RepenseSendFile")) {
                                        sendFileReply(message);
                                    } else {//6
                                        if (message.startsWith("ContactDelete")) {//6
                                            System.out.println(message);
                                            contactDelete(message);
                                        } else {//7
                                          System.out.println(message);
                                          System.out.println("protocol error");
                                        }//7
                                        
                                    }
                                }
                            }
                        }
                    }//2
                }//1

            } catch (Exception ex) {
                continu = false;
                System.out.println("La connexion est perdu");
                ex.printStackTrace();
            }

        }// contenu
        System.out.println("user déconnecté");
        try {
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception disconnect");
        }
        return null;
    }

    public void sendIM(String mes) {
        try {
            StringTokenizer StrTok = new StringTokenizer(mes, ",");
            StrTok.nextToken();
            String idDistinateur = StrTok.nextToken();
            String mespur = StrTok.nextToken();
            System.out.println("mespur= " + mespur);
            tableConnectedContacts.get(Long.parseLong(idDistinateur)).outStream.println("SendIM" + "," + uid + "," + mespur);
        } catch (Exception ex) {
            System.out.println("string tokenzer send");
            ex.printStackTrace();
        }
    }

    public void addContact(String str) {
        long id = -1;
        StringTokenizer StrTok = new StringTokenizer(str, ",");
        StrTok.nextToken();
        String pseud = StrTok.nextToken();
        try {
            jdbc = Main.dataBaseConnexionPool.take();
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException");
        }
        id = jdbc.testPseudo(pseud);
        if (id == -1) {
            outStream.println("ReponseAjouter" + "," + id);
        } else {
            ClientTask ct = null;
            ct = Main.clientsTable.get(id);
            if (ct != null) { //
                System.out.println("ct n'est pas null " + id);
                ct.outStream.println("Ajouter" + "," + uid + "," + pseudo);

            } else {
                this.outStream.println("ReponseAjouter" + "," + -2 + "," + "x" + "," + pseud);
                System.out.println("ct est  null " + id);
            }
        }

        try {
            Main.dataBaseConnexionPool.put(jdbc);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, " erreur blockingqueue put fin", "erreur", 1);
        }

    }

    public void SendFile(String message) {
        try {
            StringTokenizer StrTok = new StringTokenizer(message, ",");
            StrTok.nextToken();
            String id = StrTok.nextToken();
            String clientSerial = StrTok.nextToken();
            String nom = StrTok.nextToken();
            String taille = StrTok.nextToken();
            ClientTask ct = null;
            ct = Main.clientsTable.get(new Long(id));
            if (ct != null) {
                System.out.println("file ct n'est pas nulle");
                ct.outStream.println("SendFile" + "," + uid + "," + clientSerial + "," + nom + "," + taille);

            } else {
                this.outStream.println("ReponseSendFile" + "," + -1 + "," + id);
                System.out.println("file ct est  nulle");
            }
        } catch (Exception ex) {
            System.out.println("send file " + ex);

        }
    }
}
