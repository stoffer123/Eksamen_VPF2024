package dk.cphbusiness.vp.f2024.Eksamen.server.intf;

import java.io.IOException;

public interface ChatServer {
    void execute() throws IOException;
    void shutdown();

}
