package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.IOException;

public class Exit implements Command {
    private final String name;
    private final String description;
    private final ChatServer server;

    public Exit(ChatServer server) {
        this.name = "EXIT";
        this.description = name + " - Exits the program";
        this.server = server;

    }

    @Override
    public void execute(User user, String[] args) throws IOException {
        if(args.length != 0) {
            user.sendMessage("Exit command requires no arguments!");
            return;
        }

        user.sendMessage("Shutting down program");
        server.removeUser(user);
        user.close();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
