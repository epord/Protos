package Proxy_Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * Created by epord on 25/03/17.
 */
class ClientConnection implements Runnable {

    Socket clientSocket = null;
    Socket serverSocket = new Socket();
    byte[] bytes = new byte[64];

    public ClientConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        Logger.log("A client is now connected!!");
        InputStream sockin = null;
        OutputStream sockout = null;
        try {
            sockin = clientSocket.getInputStream();
            sockout = clientSocket.getOutputStream();


            // processing greeting
            sockin.read(bytes);
            GreetingRequest greetingRequest = new GreetingRequest(bytes);
            GreetingResponse greetingResponse = greetingRequest.process();
            sockout.write(greetingResponse.getResponse());

            // processing request
            sockin.read(bytes);
            Request request = new Request(bytes);
            Response response = new Response(request);
            sockout.write(response.getResponse(), 0, 10); // writing first 4 bytes
            if (!response.connectionAccepted()) {
                clientSocket.close();
            }

            // connecting client with server
            serverSocket = new Socket(request.getAddress(), request.getPort());
            connectSockets(serverSocket, clientSocket);

        } catch (Exception e) {
            if(sockout != null) {
                try {
                    sockout.write(e.getMessage().getBytes());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } catch (IOException e2){System.out.println(e2.getMessage());}
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    public void connectSockets(Socket s1, Socket s2) {
        new Thread(new SocketTunnel(s1, s2)).start();
        new Thread(new SocketTunnel(s2, s1)).start();
    }
}
