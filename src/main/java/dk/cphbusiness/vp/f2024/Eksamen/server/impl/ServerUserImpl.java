package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.Message;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.*;

public class ServerUserImpl implements User {
    private final ChatServer server;
    private String name;
    TextIO io;


    public ServerUserImpl(ChatServer server) {
        this.server = server;
        this.name = "SERVER";
        this.io = new SystemTextIO();

    }

    @Override
    public void run() {
        while (true) {
            String message = io.get();
            server.addMessageToQueue(new Message(this, message));
        }
    }

    @Override
    public void close() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        System.out.println("SERVER cannot change name!");
    }

    @Override
    public void sendMessage(String message) throws IOException {
        System.out.println(message);
    }

    @Override
    public void init() {

    }
}
