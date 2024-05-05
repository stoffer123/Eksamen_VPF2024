package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Broadcaster;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
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
        messages = new ArrayBlockingQueue<>(100);
    }

    @Override
    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server started with port: " + port);
            Broadcaster broadcaster = new BroadcasterImpl(messages, users);
            new Thread(broadcaster);
            while(true) {
                Socket socket = serverSocket.accept();
                User user = new UserImpl(socket);
                user.run();
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

    }

    @Override
    public void broadCast() {

    }
}
