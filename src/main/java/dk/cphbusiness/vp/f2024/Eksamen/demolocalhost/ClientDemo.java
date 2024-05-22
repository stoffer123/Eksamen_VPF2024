package dk.cphbusiness.vp.f2024.Eksamen.demolocalhost;

import dk.cphbusiness.vp.f2024.Eksamen.client.Client;
import dk.cphbusiness.vp.f2024.Eksamen.client.ClientImpl;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

public class ClientDemo {
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 23456;

        TextIO io = new SystemTextIO();
        Client client = new ClientImpl(ip, port, io);
        client.run();


    }

}
