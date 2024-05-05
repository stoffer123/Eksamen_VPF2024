package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

import dk.cphbusiness.vp.f2024.Eksamen.server.Message;

public interface ChatServer {
    void startServer();
    void stopServer();
    void addMessageToQueue(Message message);



}
