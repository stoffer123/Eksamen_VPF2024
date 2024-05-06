package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserImpl implements User {
    private final ChatServer server;
    private final Socket socket;
    private String name;
    private DataInputStream input;
    private DataOutputStream output;


    public UserImpl(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.name = "FUNGUS";
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            init();
            while (socket != null) {
                String message = input.readUTF();
                server.addMessageToQueue(new Message(this, message));
                System.out.println("[" + name + "] " + message);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void init() {
        try {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");
            String temp = input.readUTF();
            setName(temp);
            sendMessage("Welcome " + name + "!");
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
