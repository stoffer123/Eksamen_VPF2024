package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIO;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.*;

public class ServerUserImpl implements User {
    private final ChatServer server;
    private String name;
    private TextIO io;


    public ServerUserImpl(ChatServer server, TextIO io) {
        this.server = server;
        this.name = "SERVER";
        this.io = io;

    }

    @Override
    public void run() {
        while (true) {
            String message = io.get();
            server.addMessageToQueue(new MessageImpl(this, message));
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
        io.put("SERVER cannot change name!");
    }

    @Override
    public void sendMessage(String message) throws IOException {
        io.put(message);
    }
    //Overload sendMessage til at sende direkte til en bruger


    @Override
    public void init() {

    }


}
