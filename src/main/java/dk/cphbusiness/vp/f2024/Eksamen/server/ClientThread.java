package dk.cphbusiness.vp.f2024.Eksamen.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private String name;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final ChatServer chatServer;


    public ClientThread(String name, Socket socket, ChatServer chatServer) throws IOException {
        this.name = name;
        this.socket = socket;
        this.chatServer = chatServer;

        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }


    @Override
    public void run() {
        while (true) {

        }
    }
}
