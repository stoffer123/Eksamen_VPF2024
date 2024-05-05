package dk.cphbusiness.vp.f2024.Eksamen.server.interfaces;

public interface Broadcaster extends Runnable {
    @Override
    void run();
    void broadcast();
}
