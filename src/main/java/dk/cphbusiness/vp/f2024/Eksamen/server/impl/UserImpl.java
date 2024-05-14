package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class UserImpl implements User {
    private final ChatServer server;
    private final Socket socket;
    private String name;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final TextIO io;


    public UserImpl(ChatServer server, Socket socket, TextIO io) throws IOException {

        this.server = server;
        this.socket = socket;
        this.io = io;
        this.name = "FUNGUS";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

    }

    @Override
    public void run() {
        try {
            init();
            while (socket != null) {
                String message = input.readUTF();
                server.addMessageToQueue(new MessageImpl(this, message));

            }
        } catch (IOException e) {
            close();
            io.put("[SERVER] " + name + " Disconnected!");
        }

    }

    @Override
    public void init() throws IOException {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");

            //Check if name is already in use
            while(true) {
                String temp = input.readUTF();
                List<User> users = server.getUsers();
                boolean nameExists = false;

                for(User user : users) {
                    if(user.getName().toLowerCase().equals(temp.toLowerCase())) {
                        nameExists = true;
                    }
                }

                if(nameExists) {
                        sendMessage("Not a valid name, try again!");

                }else {
                    setName(temp);
                    sendMessage("Welcome " + name + "!");

                    break;

                }

            }

    }

    @Override
    public void close(){
        try {
            input.close();
            output.close();
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            io.put(e.getMessage());
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
    public void sendMessage(String message) throws IOException {
            output.writeUTF(message);
    }
}
