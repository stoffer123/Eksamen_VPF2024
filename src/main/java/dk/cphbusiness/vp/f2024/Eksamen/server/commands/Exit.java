package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;

public class Exit {
    private final String name;
    private final String description;
    private final ChatServer server;

    public Exit(ChatServer server) {
        this.name = "EXIT";
        this.description ="/" + name + " - Exits the program";
        this.server = server;

    }

    public void execute(User user) throws IOException {
        user.sendMessage("Shutting down program");
        server.removeUser(user);
        user.close();

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
