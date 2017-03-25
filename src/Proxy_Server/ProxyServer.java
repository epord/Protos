package Proxy_Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * Created by Pedro on 24/3/2017.
 */
public class ProxyServer {

    private static ServerSocket listener;
    private static String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {

        listener = new ServerSocket();
        listener.bind(new InetSocketAddress(ADDRESS, 7070));

        while(true) {
            Socket clientSocket = listener.accept();
            new Thread(new ClientConnection(clientSocket)).start();
        }
    }
}

