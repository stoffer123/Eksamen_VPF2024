package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.security.spec.EdDSAParameterSpec;

public class CommandHandler {
    private ChatServer server;

    public CommandHandler(ChatServer server) {
        this.server = server;
    }

    //WIP
    public String handle(String text, User user) {
        if(text.charAt(0) != '/') {
            return text;
        }

    }


        return "WIP";
    }


}
