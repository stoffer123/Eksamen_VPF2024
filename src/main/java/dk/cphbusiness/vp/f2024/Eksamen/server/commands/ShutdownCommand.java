package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.Role;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public class ShutdownCommand implements Command {
    private final ChatServer server;
    private final String description;

    public ShutdownCommand(ChatServer server) {
        this.server = server;
        description = "/shutdown - closes the server";
    }

    @Override
    public void execute(User user, String[] args) {
        if (user.getRole() != Role.SERVER) {
            user.sendMessage("This is a SERVER command!");
            return;
        }
        server.stopServer();

    }

    @Override
    public String getDescription() {
        return description;
    }
}
