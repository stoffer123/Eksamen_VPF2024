package dk.cphbusiness.vp.f2024.Eksamen.server;

import java.util.List;

public interface ChatServer {
    void startServer();
    void stopServer();
    void broadCastMessage(Message message);
    void removeUser(String name);
    List<ClientThread> getClients();
    void addMesageToQueue(Message message);

}
