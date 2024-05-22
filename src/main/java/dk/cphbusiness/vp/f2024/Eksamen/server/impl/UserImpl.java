package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.commands.Command;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.User;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.UserList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger.systemLogger;


public class UserImpl implements User {
    private final ChatServer server;
    private final User serverUser;
    private final Socket socket;
    private String name;
    private final DataInputStream input;
    private final DataOutputStream output;
    private boolean isRunning;
    private final UserList users;
    private Role role;


    public UserImpl(ChatServer server, User serverUser, Socket socket, UserList users) throws IOException {

        this.server = server;
        this.serverUser = serverUser;
        this.socket = socket;
        this.name = "NewUser";
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        isRunning = true;
        this.users = users;
        role = Role.USER;
    }

    @Override
    public void run() {
        init();

        try {
            while (isRunning) {
                String text = input.readUTF();
                if (text.startsWith("/")) {
                    handleCommand(text);
                } else {
                server.addMessageToQueue(new MessageImpl(this, text));
                }

            }

        } catch (IOException e) {
            String errorMsg = "Lost connection to: " + name + " with message: " + e.getMessage();
            systemLogger.warning(errorMsg);
        } finally {
            close();
        }

    }

    @Override
    public void init() {
        try {
            sendMessage("Connected to " + socket.getRemoteSocketAddress());
            sendMessage("Please enter your name: ");

            //Check if name is already in use
            while (name.equalsIgnoreCase("NewUser")) {
                String temp = input.readUTF().trim();

                if (users.nameExists(temp)) {
                    sendMessage("Not a valid name, try again!");

                } else {
                    setName(temp);
                    server.addMessageToQueue(new MessageImpl(serverUser, "Welcome " + name + "!"));
                    break;

                }

            }
        } catch (IOException e) {
            close();
        }

    }

    @Override
    public void close(){
        //Ensure close() is only called once
        if(!isRunning) return;

        isRunning = false;
        //close resources individually to make sure everything gets closed.
        try{
            if(input != null) {
            input.close();
            }

        }catch(IOException e) {
            systemLogger.warning(name + " Failed to close inputStream " + e.getMessage());
        }

        try{
            if(output != null) {
            output.close();
            }

        }catch(IOException e) {
            systemLogger.warning(name + " Failed to close outputStream " + e.getMessage());
        }

        try{
            if(socket != null) {
            socket.close();
            }

        }catch(IOException e) {
            systemLogger.warning(name + " Failed to close socket " + e.getMessage());
        }
        server.addMessageToQueue(new MessageImpl(serverUser, name + " Disconnected!"));
        server.removeUser(this);
        systemLogger.info(name + " Disconnected!");

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        systemLogger.info(this.name + " changed name to: " + name);
        this.name = name;
    }

    @Override
    public void sendMessage(String text) {
        //sendMessage, possibly should have had Message as parameter and convert it to String inside this method
        try {
            output.writeUTF(text);
        }catch (IOException e) {
            systemLogger.warning("sendMessage() failed for user: " + name + " with message: " + e.getMessage());
        }
    }

    @Override
    public void handleCommand(String text) {
        String[] parts = text.split(" ");
        String commandName = parts[0].substring(1);
        Command command = server.getCommand(commandName);

        if(command != null) {
            command.execute(this, parts);
            systemLogger.info(this.name + " used /" + commandName);
        } else {
            sendMessage("Unknown command: " + commandName);
        }

    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }
}
