package dk.cphbusiness.vp.f2024.Eksamen.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServerImpl implements ChatServer {
    private final int port;
    private List<ClientThread> clientList;
    private BlockingQueue<Message> messageQueue;


    public ChatServerImpl(int port) {
        this.port = port;
        clientList = new ArrayList();
        messageQueue = new LinkedBlockingQueue();

    }


    @Override
    public void startServer() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread("NewUser", socket, this);
                clientThread.start();
                clientList.add(clientThread);

            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @Override
    public void stopServer() {

    }

    @Override
    public void addMesageToQueue(Message message) {
        messageQueue.add(message);

    }

    @Override
    public void broadCastMessage(Message message) {

    }

    @Override
    public void removeUser(String name) {

    }

    @Override
    public List<ClientThread> getClients() {
        return List.of();
    }
}
