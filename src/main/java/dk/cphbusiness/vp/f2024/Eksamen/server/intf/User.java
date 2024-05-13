package dk.cphbusiness.vp.f2024.Eksamen.server.intf;

public interface User extends Runnable {
    @Override
    void run();
    void stop();
    void init();

    void sendMessage(String message);
    void listenForMessages();
    String getUsername();
    void setUsername(String username);

}
