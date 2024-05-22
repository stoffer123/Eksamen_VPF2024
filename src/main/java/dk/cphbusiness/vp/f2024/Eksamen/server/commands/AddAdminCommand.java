package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.Role;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger.systemLogger;

public class AddAdminCommand implements Command {
    private final UserList users;
    private final String description;

    public AddAdminCommand(UserList users) {
        this.users = users;
        description = "/addadmin <username> - assigns admin rights to user";
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
        if(userRole != Role.SERVER) {
            user.sendMessage("This is a SERVER command!");
            return;
        }


        //Check amount of arguments
        if(args.length != 2) {
            user.sendMessage("Too few/many arguments - usage: /addadmin <target>");
            return;
        }

        //Try to find the username and assign a target
        for(User u : users.getUsers()) {
            if(u.getName().equalsIgnoreCase(args[1])) {
                target = u;
            }
        }

        //If username was found, add admin powers to the user
        if(target != null) {
            target.sendMessage("You received admin rights by: " + user.getName());
            target.setRole(Role.ADMIN);
            systemLogger.info(user.getName() + " assigned ADMIN rights to " + target.getName());

        } else {
            user.sendMessage("No such user found!");
        }

    }
}
