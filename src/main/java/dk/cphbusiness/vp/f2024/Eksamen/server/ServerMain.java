package dk.cphbusiness.vp.f2024.Eksamen.server;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.ChatServerImpl;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

public class ServerMain {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: java Main <port>");
            System.exit(0);
        }
        int port = Integer.parseInt(args[0]);
        TextIO io = new SystemTextIO();
        ChatServerImpl server = new ChatServerImpl(port, io);
        server.startServer();

    }


}
