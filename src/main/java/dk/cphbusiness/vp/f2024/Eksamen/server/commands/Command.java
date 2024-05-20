package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

public interface Command {
    void execute(User user, String[] args);
    String getDescription();
}
