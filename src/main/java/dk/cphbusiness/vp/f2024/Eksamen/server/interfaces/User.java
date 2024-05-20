package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

public interface User extends Runnable {
     void run();
     void close();
     String getName();
     void setName(String name);
     void sendMessage(String message);
     void init();
     void handleCommand(String text);

}
