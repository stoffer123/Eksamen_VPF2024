package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.Role;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.ServerLogger.logger;

public class KickCommand implements Command {
    private final UserList users;
    private final String description;

    public KickCommand(UserList users) {
        this.users = users;
        description = "/kick <username> - Kicks the specified user";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(User user, String[] args) {
        User target = null;
        Role userRole = user.getRole();

        //Check if user is ADMIN or SERVER
        if(userRole != Role.SERVER && userRole != Role.ADMIN) {
            user.sendMessage("This is an admin command!");
            return;
        }


        //Check amount of arguments
        if(args.length != 2) {
            user.sendMessage("Too few/many arguments - usage: /kick <target>");
            return;
        }

        //Try to find the username and assign a target
        for(User u : users.getUsers()) {
            if(u.getName().equalsIgnoreCase(args[1])) {
                target = u;
            }
        }

        //If username was found close the user else say no user was found
        if(target != null) {
            target.sendMessage("You have been kicked by: " + user.getName());
            target.close();
            logger.info(target.getName() + " KICKED " + "by: " + user.getName());

        } else {
            user.sendMessage("No such user found!");
        }

    }

}
