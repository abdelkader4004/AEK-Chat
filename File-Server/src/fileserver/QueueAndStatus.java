/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 *
 * @author user
 */
public class QueueAndStatus {
    BlockingQueue<byte[]> bytesQueue=new LinkedBlockingQueue<byte[]>(2694);
    int status = 0;
    long upLoaded=0;
}
