package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

import dk.cphbusiness.vp.f2024.Eksamen.server.commands.Command;


public interface ChatServer {
    void startServer();
    void stopServer();
    void addMessageToQueue(Message message);
    void removeUser(User user);
    boolean isOnline();
    void registerCommands();
    Command getCommand(String name);



}
