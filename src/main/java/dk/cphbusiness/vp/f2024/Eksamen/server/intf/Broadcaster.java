package dk.cphbusiness.vp.f2024.Eksamen.server.intf;

public interface Broadcaster extends Runnable {
    @Override
    void run();
    void stop();
    void broadcast();


}
