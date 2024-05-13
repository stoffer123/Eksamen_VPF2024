package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

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
    ServerSocket serverSocket;
    BlockingQueue<Message> messages;


    public ChatServerImpl(int port) {
        this.port = port;
        users = new ArrayList<>();
        messages = new LinkedBlockingQueue<>(100);
    }

    @Override
    public void startServer() {
        try {
            //Create serverSocket, Broadcaster and serverUser
            serverSocket = new ServerSocket(port);
            Broadcaster broadcaster = new BroadcasterImpl(messages, users);
            new Thread(broadcaster).start();
            User server = new ServerUserImpl(this);
            new Thread(server).start();
            users.add(server);
            System.out.println("Server started with port: " + port);


            //Start listening for clients
            while(true) {
                Socket socket = serverSocket.accept();
                User user = new UserImpl(this, socket);
                users.add(user);
                new Thread(user).start();

            }
        }catch(IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void stopServer() {
        System.out.println("Stopping server");
        for(User user : users) {
            user.close();
            System.out.println(user.getName() + " Disconnected by the server");
        }
        users.clear();
        try {
        serverSocket.close();

        System.exit(0);
        }catch(IOException e) {
            System.out.println(e);
        }




    }

    @Override
    public void addMessageToQueue(Message message) {
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
