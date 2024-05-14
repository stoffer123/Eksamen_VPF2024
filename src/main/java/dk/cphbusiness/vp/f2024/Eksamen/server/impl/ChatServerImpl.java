package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServerImpl implements ChatServer {
    private int port;
    private List<User> users;
    private final TextIO io;
    private ServerSocket serverSocket;
    private  BlockingQueue<MessageImpl> messages;


    public ChatServerImpl(int port, TextIO io) {
        this.port = port;
        this.io = io;
        users = new ArrayList<>();
        messages = new LinkedBlockingQueue<>(100);
    }

    @Override
    public void startServer() {
        try {
            //Create serverSocket, Broadcaster and serverUser
            serverSocket = new ServerSocket(port);
            Broadcaster broadcaster = new BroadcasterImpl(messages, users, io);
            new Thread(broadcaster).start();
            User serverUser = new ServerUserImpl(this, io);
            new Thread(serverUser).start();
            users.add(serverUser);
            io.put("Server started with port: " + port);


            //Start listening for clients
            while(true) {
                Socket socket = serverSocket.accept();
                User user = new UserImpl(this, socket, io);
                users.add(user);
                new Thread(user).start();
                serverUser.sendMessage("A new user has joined, waiting for name....");

            }
        }catch(IOException e) {
            io.put(e.getMessage());
        }
    }

    @Override
    public void stopServer() {
        io.put("Stopping server");
        for(User user : users) {
            user.close();
            io.put(user.getName() + " Disconnected by the server");
        }
        users.clear();
        try {
        serverSocket.close();

        System.exit(0);
        }catch(IOException e) {
            io.put(e.getMessage());
        }




    }

    @Override
    public void addMessageToQueue(MessageImpl message) {
        messages.add(message);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
