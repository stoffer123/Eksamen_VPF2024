package dk.cphbusiness.vp.f2024.Eksamen.server.commands;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;

import java.util.Map;
import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.ServerLogger.logger;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;
    private final String description;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
        this.description = "/help - Lists all available commands";
    }

    @Override
    public void execute(User user, String[] args) {
        StringBuilder response = new StringBuilder("Available commands:\n");
        for (Command command : commands.values()) {
            response.append(command.getDescription()).append("\n");
        }
        logger.info(user.getName() + " Used /HELP");
        user.sendMessage(response.toString());

    }

    @Override
    public String getDescription() {
        return description;
    }
}
