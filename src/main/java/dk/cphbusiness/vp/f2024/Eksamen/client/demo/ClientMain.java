package dk.cphbusiness.vp.f2024.Eksamen.client.demo;

import dk.cphbusiness.vp.f2024.Eksamen.client.Client;
import dk.cphbusiness.vp.f2024.Eksamen.client.ClientImpl;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        TextIO io = new SystemTextIO();
            //Find port register på stackoverflow
            Client client = new ClientImpl("127.0.0.1", 23456, io);
            client.run();


    }
}
