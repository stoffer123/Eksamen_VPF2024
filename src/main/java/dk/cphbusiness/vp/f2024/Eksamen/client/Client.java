package dk.cphbusiness.vp.f2024.Eksamen.client;

public interface Client extends Runnable {
    @Override
    void run();
    void stop();
    void sendMessage(String message);
    void receiveMessage(String message);

}
