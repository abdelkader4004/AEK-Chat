package client;

import GUI.ChatPanel;
import javax.swing.JPanel;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */
public interface ObserverInterface {

    public void setTitre(String s);

    public void setClient(Client client);

    public void connexionOK(String pseudo);

    public void removePane(JPanel p);

    public void removeConnectionPane();

    public void addPane(JPanel p, String text);

    public void startAnimationPane();

    public void stopAnimation();

    public void startListening();

    public void setContactsListPane();

    public void addContact(int state, OurString name);

    public void changeState(String id, String etas);

    public void addMessage(String mes, String pseudo);

    public void addChatPane(ChatPanel p, String s);

    public void addPaneCreation();

    public void removePaneCreation();

    public void addConnectionPane();
    public void result(int task,int authenticated);
}
