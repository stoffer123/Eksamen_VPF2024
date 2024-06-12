package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.commands.Command;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger.systemLogger;

public abstract class AbstractUser implements User {
    protected final ChatServer server;
    protected String name;

    public AbstractUser(ChatServer server, String name) {
        this.server = server;
        this.name = name;
    }

    public void handleCommand(String text) {
        String[] parts = text.split(" ");
        String commandName = parts[0].substring(1);
        Command command = server.getCommand(commandName);

        if(command != null) {
            command.execute(this, parts);
            systemLogger.info(this.name + " used /" + commandName);
        } else {
            sendMessage("Unknown command: " + commandName);
        }

    }

}
