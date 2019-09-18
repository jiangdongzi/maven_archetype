
package com.journaldev.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * This class implements java socket client
 * @author pankaj
 *
 */
public class ClientExample {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        for (int i = 0; i < 5; i++) {
            //establish socket connection to server
            socket = new Socket("10.236.93.197", 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            while (true) {
                System.out.println("Sending request to Socket Server");
                if (i == 4) oos.writeObject("exit");
                else oos.writeObject("hello__world" + i);
                //read the server response message
                System.out.println("read in client");
                byte[] buf = new byte[1024];
                int reCnt = is.read(buf);
                System.out.println(reCnt);
                Thread.sleep(3000);
//                System.out.println(Arrays.toString(buf) + "::" + reCnt);
            }
        }
    }
}

