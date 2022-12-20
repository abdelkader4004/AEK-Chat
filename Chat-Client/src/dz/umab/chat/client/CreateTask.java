/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import GUI.MainFrame;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class CreateTask implements Callable {

    Client client;
    String login = "", pass = "";
    ObserverInterface observer;
    String cordonne = "";

    public CreateTask(String cordonne,ObserverInterface observer) {
this.observer = observer;
        this.cordonne = cordonne;
    }

    public Object call() {
        int result;
        String message = "";
        System.out.println("CreateTask");
        try {
            Thread.sleep(800);
        } catch (InterruptedException ex) {
            Logger.getLogger(CreateTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (MainFrame.client.creeConnexion()) {
            MainFrame.client.register(cordonne);
            message = MainFrame.client.ReadMessage();
            if (message.startsWith("ok")) {
                result = 1;
                System.out.println("CreateTask ok");
            } else {
                result = 0;
                System.out.println("CreateTask ko");

            }
        } else {
            result = -1;
            System.out.println("CreateTask no Server ");
        }
         SwingUtilities.invokeLater(new RunnableImpl(result));
        
        return 0;
    }
        private class RunnableImpl implements Runnable {

        private final int result;

        public RunnableImpl(int result) {
            this.result = result;
        }

        public void run() {
            observer.result(1, result);
        }
    }
}