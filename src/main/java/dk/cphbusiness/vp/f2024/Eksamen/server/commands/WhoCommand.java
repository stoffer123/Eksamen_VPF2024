package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;

public class WhoCommand implements Command {
    private final UserList users;
    private final String description;

    public WhoCommand(UserList users) {
        this.users = users;
        this.description = "/who - Lists all connected users";
    }

    @Override
    public void execute(User user, String[] args) {
        StringBuilder response = new StringBuilder("Connected users:\n");
        for (User u : users.getUsers()) {
            response.append("[" + u.getRole().name() + "] " + u.getName()).append("\n");
        }

        user.sendMessage(response.toString());
    }

    @Override
    public String getDescription() {
        return description;
    }
}
