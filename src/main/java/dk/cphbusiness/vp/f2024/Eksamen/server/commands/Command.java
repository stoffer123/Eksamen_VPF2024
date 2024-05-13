package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.io.IOException;

public interface Command {
    void execute(User user, String[] args) throws IOException;
    String getName();
    String getDescription();

}
