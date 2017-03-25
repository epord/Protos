package Proxy_Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
public class SocketTunnel implements Runnable{
    Socket s1;
    Socket s2;
    byte[] buffer = new byte[4096];

    public SocketTunnel(Socket s1, Socket s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void run() {
        try {
            InputStream is = s1.getInputStream();
            OutputStream os = s2.getOutputStream();

            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = is.read(buffer, 0, buffer.length);
                if (bytesRead != -1) {
                    os.write(buffer, 0, bytesRead);
                    System.out.println("\n\n\n  ----------\n\n\n" + new String(buffer));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}