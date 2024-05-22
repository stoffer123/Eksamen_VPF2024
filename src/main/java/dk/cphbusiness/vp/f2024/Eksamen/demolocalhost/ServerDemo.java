package dk.cphbusiness.vp.f2024.Eksamen.demolocalhost;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.ChatServerImpl;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

public class ServerDemo {
    public static void main(String[] args) {
        int port = 23456;
        String logFilePath = "C:\\Users\\chris\\Desktop\\logger_Test";
        TextIO io = new SystemTextIO();

        ChatServerImpl server = new ChatServerImpl(port, io, logFilePath);
        server.startServer();

    }


}
