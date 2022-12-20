/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import GUI.MainFrame;
import GUI.BreakingEventWindow;
import GUI.FileReceivePane;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author user
 */
public class ReceiveTask implements Runnable {

    private Socket socket;
    ObserverInterface observer;
    // persistant par les autre 
    volatile boolean tchating = true;
    Client client;
    String message = "";
    StringTokenizer st1, st2;

    public ReceiveTask(ObserverInterface observer, Client client) {
        this.observer = observer;
        this.client = client;
        socket = client.getSocket();
    }

    public void stop() {
        tchating = false;
    }

    public void run() {
        String id = "";
        String login = "";
        String etat = "";
        //receive contacts list
        message = client.ReadMessage();
        System.out.println("reçu= " + message);
        st1 = new StringTokenizer(message, ":");
        while (st1.hasMoreTokens()) {
            st2 = new StringTokenizer(st1.nextToken(), ",");
            id = st2.nextToken();
            login = st2.nextToken();
            client.addConact(new Long(id), login);
            observer.addContact(0, new OurString(login));
        }
        message = client.ReadMessage();
        System.out.println("mess etats = " + message);
        st1 = new StringTokenizer(message, ":");
        st1.nextToken();
        while (st1.hasMoreTokens()) {
            st2 = new StringTokenizer(st1.nextToken(), ",");
            etat = st2.nextToken();
            id = st2.nextToken();
            System.out.println("id== " + id + "   etat= " + etat + "  pseudo=  " + client.getPseudo(new Long(id)));
            //observer.changeState(client.getPseudo(id), etat);
            observer.changeState(client.getPseudo(new Long(id)), "1");


        //System.out.println("contact= " + st2.nextToken());


        }


        client.showlist();

        while (tchating) {

            message = "";
            message = client.ReadMessage();
            System.out.println(message);
            st2 = new StringTokenizer(message, ",");
            if (message.startsWith("change")) {
                st2.nextToken();
                id = st2.nextToken();
                etat = st2.nextToken();
                observer.changeState(client.getPseudo(new Long(id)), etat);
                if (Integer.parseInt(etat) == 0) {
                    new BreakingEventWindow("Deconnexion", client.getPseudo(new Long(id)) + " vient de se deconnecter ");

                } else {
                    new BreakingEventWindow("Connexion", client.getPseudo(new Long(id)) + " vient de se connecter ");
                }
            //new GUI.BreakingEventWindow();
            // System.out.println(message);
            } else {
                if (message.startsWith("SendIM")) {
                    st2.nextToken();
                    String idEmetteur= st2.nextToken();
                    String mespur = st2.nextToken();
                    observer.addMessage(mespur, idEmetteur);
                } else {
                    if (message.startsWith("ReponseAjouter")) {
                        reponseAjouter(message);
                    } else {
                        if (message.startsWith("Ajouter")) {
                            Ajouter(message);
                        } else {
                            if (message.startsWith("ReponseSendFile")) {
                                reponseSendFile(message);
                            } else {
                                if (message.startsWith("SendFile")) {
                                    SendFile(message);

                                } else {
                                    if (message.startsWith("Serial")) {
                                        setSerial(message);

                                    }
                                }

                            //  

                            }//else resend



                        }//else ajouter




                    }// else  Rajouter
                }//else send
            }// else change
        }// while tachatin
    }// function

    public void reponseAjouter(String message) {
        String rep = "";
        st1 = new StringTokenizer(message, ",");
        st1.nextToken();
        rep = st1.nextToken();
        System.out.println("repense" + rep);
        if ("-1".equals(rep)) {
            new BreakingEventWindow("Information", "ce pseudo n'existe pas ");
        } else {
            String id = st1.nextToken();
            String pseudo = st1.nextToken();
            if ("-2".equals(rep)) {
                new BreakingEventWindow("Information", pseudo + " n'est pas accessible ");
            } else {
                if ("1".equals(rep)) {
                    new BreakingEventWindow("Information", pseudo + " a accepter de vous  ajouter");

                    observer.addContact(1, new OurString(pseudo));
                    MainFrame.client.addConact(new Long(id), pseudo);
                } else {
                    System.out.println("repense inconnue");
                }
            }
        }
    }

    public void Ajouter(String message) {
        String id = "";
        String pseudo = "";
        st1 = new StringTokenizer(message, ",");
        st1.nextToken();
        id = st1.nextToken();
        pseudo = st1.nextToken();
        new BreakingEventWindow("Confirmation", pseudo + " souhaite de vous  agouter", MainFrame.contactListPanel, id + "," + pseudo);


    }

    private void reponseSendFile(String message) {
        try {
            st1 = new StringTokenizer(message, ",");
            st1.nextToken();
            String rep = st1.nextToken();
            String id = st1.nextToken();
            String localSerial = st1.nextToken();
            String serial = st1.nextToken();
            if ("-1".equals(rep)) {
                new BreakingEventWindow("Information", MainFrame.client.getPseudo(new Long(id)) + " n'est pas accessible");
            } else {
                if ("1".equals(rep)) {
                    new BreakingEventWindow("Information", " le transfer va commencer vers " + MainFrame.client.getPseudo(new Long(id)));
                    MainFrame.client.fileTable.get(new Long(localSerial)).startTransfert(4l);
                    MainFrame.client.addFielSerial(new Long(serial), new Long(localSerial));
                //MainFrame.client.WriteMessage("Send" + "," + 4);
                } else {
                    System.out.println("repense inconnue");
                }
            }
        } catch (Exception e) {
            System.out.println("rep exception " + "" + e);
        }
    }

    private void SendFile(String message) {

        System.out.println("SendFile appelée");
        try {
            st1 = new StringTokenizer(message, ",");
            st1.nextToken();
            String id = st1.nextToken();
            String remoteSerial = st1.nextToken();
            String nomFile = st1.nextToken();
            String FileSize = st1.nextToken();
            FileReceivePane fileReceivePane = new FileReceivePane(observer);
            new BreakingEventWindow("Confirmation", MainFrame.client.getPseudo(new Long(id)) + " souhaite de vous envoyer le fichier " + nomFile + "de taille" + FileSize, fileReceivePane, nomFile + "," + FileSize + "," + id + "," + remoteSerial);
        } catch (Exception e) {
            System.out.println("SendFile exception " + "" + e);
        }
        System.out.println("SendFile terminée sans exceptions");
    }

    private void setSerial(String message) {
        st1 = new StringTokenizer(message, ",");
        st1.nextToken();
        String localSerial = st1.nextToken();
        String serial = st1.nextToken();
        MainFrame.client.addFielSerial(new Long(serial), new Long(localSerial));
        MainFrame.client.fileTable.get(new Long(localSerial)).startTransfert(4l);
    }
}
