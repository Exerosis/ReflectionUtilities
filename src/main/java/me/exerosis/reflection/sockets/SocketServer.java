package me.exerosis.reflection.sockets;

import me.exerosis.reflection.util.MultiThreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class SocketServer implements MultiThreaded {
    private ServerSocket listeningSocket;
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        runNewThread(() -> {
            try {
                listeningSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + port);
                System.exit(-1);
            }

            while (listeningSocket != null && !listeningSocket.isClosed()) {
                final Socket[] socket = new Socket[1];
                try {
                    socket[0] = listeningSocket.accept();
                } catch (IOException e) {
                    System.err.println("Could not accept an incoming connection!");
                } finally {
                    if (socket[0] != null)
                        runNewThread(() -> onAttemptConnection(socket[0]));
                }
            }
        });
    }

    public abstract void onAttemptConnection(Socket socket);

    public void close() {
        try {
            listeningSocket.close();
            listeningSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}