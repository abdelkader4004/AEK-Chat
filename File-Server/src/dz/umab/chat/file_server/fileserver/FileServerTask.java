/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class FileServerTask implements Callable {

    Socket socket;
    BlockingQueue<byte[]> bytesQueue;
    private BufferedOutputStream outStream;
    private BufferedInputStream inStream;
   
    long serial = 0;
    QueueAndStatus queueAndStatus;

    private void produce() {
        int n = 0;
        System.out.println("produce appelée");
        byte[] buffer,b;
        try {
            buffer = new byte[10000];
            while ((n = inStream.read(buffer)) >= 0) {
                try {
                    b=new byte[n];
                    for(int j=0;j<n;j++)
                    b[j]=buffer[j];
                    bytesQueue.put(b);
                    queueAndStatus.upLoaded += n;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // buffer = new byte[1];
            }
            queueAndStatus.status = 1;
            System.out.println("produce terminée " + queueAndStatus.upLoaded);

        } catch (IOException ex) {
           ex.printStackTrace();
        }

    }

    private void consume() {
        System.out.println("consume appelée");
        byte[] buffer ;
        long downLoaded = 0;
        try {
            while ((queueAndStatus.status == 0) || (queueAndStatus.upLoaded > downLoaded)) {
                buffer = bytesQueue.take();
                try {
                    outStream.write(buffer);
                    downLoaded += buffer.length;
                    outStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("consume terminée " + downLoaded);
    }

    public FileServerTask(Socket socket) {
        this.socket = socket;
        try {
            outStream = new BufferedOutputStream(socket.getOutputStream());
            inStream = new BufferedInputStream(socket.getInputStream());
           // inString = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Logger.getLogger(FileServerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object call() throws Exception {
        String message = "";
        try {
           // message = inString.readLine();
             boolean b = true;
            byte[] buffer = new byte[1];
            int n = -1;
            while (b) {
                if ((n = inStream.read(buffer)) > 0) {
                    if (buffer[0] != '\n') {
                        message = message + new String(buffer, "UTF8");
                    } else {
                        b = false;
                    }
                }
            }
          //  inString.close();
            System.out.println("message =" + message);
            StringTokenizer st = new StringTokenizer(message, ",");
            st.nextToken();
            serial = new Long(st.nextToken());
            System.out.println("serial ..." + serial);
            queueAndStatus = FileServerMain.table.get(serial);
            bytesQueue = queueAndStatus.bytesQueue;

            if (message.startsWith("Send000")) {
                System.out.println("send000");
                produce();
            }
            if (message.startsWith("Receive")) {
                System.out.println("receive");
                consume();
            }
        } catch (Exception e) {
            System.out.println("nn" + e);
            e.printStackTrace();
        }
        return null;
    }
}
