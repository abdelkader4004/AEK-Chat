package dz.umab.chat.dskclient.client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Soci�t� : </p>
 *
 * @author non attribuable
 * @version 1.0
 */

public class ServerListener extends Thread {
    private BufferedReader din = null;

    public ServerListener(BufferedReader din) {
        this.din = din;
        System.out.println("i am listening...");
    }

    @Override
    public void run() {
        while (true) {

            try {
                System.out.println(din.readLine());

            } catch (IOException ex) {
                System.out.println("exception io listener");
            }
        }

    }

}
