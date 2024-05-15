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
    private final User serverUser;
    private final Socket socket;
    private String name;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final TextIO io;
    private boolean isRunning;


    public UserImpl(ChatServer server, User serverUser, Socket socket, TextIO io) throws IOException {

        this.server = server;
        this.serverUser = serverUser;
        this.socket = socket;
        this.io = io;
        this.name = "NewUser";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        isRunning = true;
    }

    @Override
    public void run() {
        try {
            init();
            while (isRunning) {
                String text = input.readUTF();
                server.addMessageToQueue(new MessageImpl(this, text));

            }
            close();

        } catch (IOException e) {
            close();
            io.putError(e.getMessage() + " for user: " + name + " in run()");
        }

    }

    @Override
    public void init() throws IOException {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");

            //Check if name is already in use
            while(true) {
                String temp = input.readUTF().trim();
                List<User> users = server.getUsers();
                boolean nameExists = false;

                for(User user : users) {
                    if(user.getName().equalsIgnoreCase(temp)) {
                        nameExists = true;
                    }
                }

                if(nameExists) {
                        sendMessage("Not a valid name, try again!");

                }else {
                    setName(temp);
                    server.addMessageToQueue(new MessageImpl(serverUser, "Welcome " + name + "!"));

                    break;

                }

            }

    }

    @Override
    public void close(){
        try {
            isRunning = false;
            input.close();
            output.close();
            socket.close();
            server.removeUser(this);
        } catch (IOException e) {
            io.putError("close() failed for user: " + name + " with message: " + e.getMessage());
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
    public void sendMessage(String text) throws IOException {
            output.writeUTF(text);
    }
}
