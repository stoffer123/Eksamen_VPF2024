package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.MessageImpl;

import java.util.List;

public interface ChatServer {
    void startServer();
    void stopServer();
    void addMessageToQueue(Message message);
    void removeUser(User user);
    List<User> getUsers();
    boolean isOnline();



}
