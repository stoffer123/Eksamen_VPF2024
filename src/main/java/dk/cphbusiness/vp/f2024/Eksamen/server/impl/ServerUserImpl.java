package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.commands.Command;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;


public class ServerUserImpl implements User {
    private final ChatServer server;
    private final String name;
    private TextIO io;
    private Role role;


    public ServerUserImpl(ChatServer server, TextIO io) {
        this.server = server;
        this.name = "SERVER";
        this.io = io;
        role = Role.SERVER;

    }

    @Override
    public void run() {
        while (server.isOnline()) {
            String text = io.get();

            if (text.startsWith("/")) {
                handleCommand(text);
            } else {
                server.addMessageToQueue(new MessageImpl(this, text));
            }
        }
    }

    @Override
    public void close() {
        //not needed in serverUserImpl
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
    public void sendMessage(String text) {
        io.put(text);
    }


    @Override
    public void init() {
        //not needed in serverUser
    }

    @Override
    public void handleCommand(String text) {
        String[] parts = text.split(" ");
        String commandName = parts[0].substring(1);
        Command command = server.getCommand(commandName);

        if(command != null) {
            command.execute(this, parts);
        } else {
            sendMessage("Unknown command: " + commandName);
        }
    }

    @Override
    public Role getRole() {
        return role;
    }
}
