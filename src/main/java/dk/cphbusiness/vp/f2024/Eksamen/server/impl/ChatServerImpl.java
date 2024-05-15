package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
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
    private BlockingQueue<Message> messages;
    private boolean isOnline;


    public ChatServerImpl(int port, TextIO io) {
        this.port = port;
        this.io = io;
        users = new ArrayList<>();
        messages = new LinkedBlockingQueue<>(100);
        isOnline = true;
    }

    @Override
    public void startServer() {
        try {
            //Create serverSocket, Broadcaster and serverUser
            serverSocket = new ServerSocket(port);
            Broadcaster broadcaster = new BroadcasterImpl(messages, this, io);
            new Thread(broadcaster).start();
            User serverUser = new ServerUserImpl(this, io);
            new Thread(serverUser).start();
            users.add(serverUser);
            io.put("Server started with port: " + port);

            //Breaks on 2 simultaneous connections
            //solution might be to store socket in a blockingqueue and have a thread for creating clients?
            while(isOnline) {
                Socket socket = serverSocket.accept();
                addMessageToQueue(new MessageImpl(serverUser, "A new user has joined, waiting for name..."));
                User user = new UserImpl(this, serverUser, socket, io);
                users.add(user);
                new Thread(user).start();

            }
        }catch(IOException e) {
            io.put(e.getMessage());
        }
    }

    @Override
    public void stopServer() {
        io.put("Stopping server");
        isOnline = false;
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
    public void addMessageToQueue(Message message) {
        messages.offer(message);

    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }
}
