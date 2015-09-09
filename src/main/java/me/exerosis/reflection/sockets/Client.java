package me.exerosis.reflection.sockets;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String hostname;
    private final int port;
    private SocketListener socketListener = null;

    public Client(String hostname, int port, ObjectReceiver objectReceiver) {
        this.hostname = hostname;
        this.port = port;
        try {
            socketListener = new SocketListener(new Socket(hostname, port), objectReceiver);
        } catch (IOException e) {
            System.err.println("Unable to connect to a server at '" + hostname + ":" + port + "'");
        }
    }


    public ObjectReceiver getObjectReceiver() {
        return socketListener.getObjectReceiver();
    }

    public void setObjectReceiver(ObjectReceiver objectReceiver) {
        socketListener.setObjectReceiver(objectReceiver);
    }

    public int getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }
}
