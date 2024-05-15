package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;
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
    private UserList users;


    public UserImpl(ChatServer server, User serverUser, Socket socket, TextIO io, UserList users) throws IOException {

        this.server = server;
        this.serverUser = serverUser;
        this.socket = socket;
        this.io = io;
        this.name = "NewUser";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        isRunning = true;
        this.users = users;
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
            while(server.isOnline()) {
                String temp = input.readUTF().trim();

                if(users.nameExists(temp)) {
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
