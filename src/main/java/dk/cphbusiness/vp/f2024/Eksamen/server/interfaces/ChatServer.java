package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

public interface ChatServer {
    void startServer();
    void stopServer();
    void addMessageToQueue(Message message);
    void broadCast();


}
