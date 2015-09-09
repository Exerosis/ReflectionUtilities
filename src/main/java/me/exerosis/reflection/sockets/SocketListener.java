package me.exerosis.reflection.sockets;

import me.exerosis.reflection.util.MultiThreaded;

import java.io.*;
import java.net.Socket;

/**
 * x
 * Created by The Exerosis on 8/5/2015.
 */
public class SocketListener implements MultiThreaded {
    private Socket socket;
    private ObjectReceiver receiver;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public SocketListener(Socket socket) {
        this(socket, object -> {
        });
    }

    public SocketListener(Socket socket, ObjectReceiver objectReceiver) {
        this.socket = socket;
        receiver = objectReceiver;
        runNewThread(() -> {
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                outputStream = new ObjectOutputStream(new BufferedOutputStream(out));
                inputStream = new ObjectInputStream(new BufferedInputStream(in));
            } catch (IOException ignored) {
            }

            while (socket.isConnected()) {
                Object object = null;
                try {
                    object = inputStream.readObject();
                } catch (Exception ignored) {
                } finally {
                    if (object != null)
                        receiver.onObjectReceived(object);
                }
            }
        });
    }

    public ObjectReceiver getObjectReceiver() {
        return receiver;
    }

    public void setObjectReceiver(ObjectReceiver objectReceiver) {
        receiver = objectReceiver;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void close() {
        try {
            socket.close();
            outputStream.close();
            inputStream.close();
        } catch (IOException ignored) {
        }
    }

    public void sendObject(Object object) {
        if (socket == null || socket.isClosed() || !socket.isConnected())
            return;
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException ignored) {
        }
    }
}