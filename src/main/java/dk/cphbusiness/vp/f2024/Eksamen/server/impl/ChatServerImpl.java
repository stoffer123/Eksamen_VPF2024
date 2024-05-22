package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.commands.*;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.*;
import dk.cphbusiness.vp.f2024.Eksamen.server.logger.ChatLogger;
import dk.cphbusiness.vp.f2024.Eksamen.server.logger.SystemLogger;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServerImpl implements ChatServer {
    private final int port;
    private final TextIO io;
    private ServerSocket serverSocket;
    private final UserList users;
    private final BlockingQueue<Message> messages;
    private boolean isOnline;
    private final String logFilePath;
    private final Map<String, Command> commands;
    private final SystemLogger systemLogger;
    private final ChatLogger chatLogger;


    public ChatServerImpl(int port, TextIO io, String logFilePath) {
        this.port = port;
        this.io = io;
        users = new UserListImpl(this, io);
        messages = new LinkedBlockingQueue<>(100);
        isOnline = true;
        this.logFilePath = logFilePath;
        commands = new HashMap<>();
        systemLogger = new SystemLogger(this, logFilePath);
        chatLogger = new ChatLogger(this, logFilePath);
        registerCommands();


        SystemLogger.systemLogger.info("Server started on port " + port);

    }


    @Override
    public void startServer() {
        try {
            //Create serverSocket, Broadcaster and serverUser
            serverSocket = new ServerSocket(port);
            Broadcaster broadcaster = new BroadcasterImpl(messages, this, io, users);
            new Thread(broadcaster).start();
            User serverUser = new ServerUserImpl(this, io);
            new Thread(serverUser).start();
            users.addUser(serverUser);

            while(isOnline) {
                Socket socket = serverSocket.accept();
                addMessageToQueue(new MessageImpl(serverUser, "A new user has joined, waiting for name..."));
                User user = new UserImpl(this, serverUser, socket, io, users);
                users.addUser(user);
                new Thread(user).start();

                SystemLogger.systemLogger.info("NewUser joined the server!");

            }

        }catch(IOException e) {
            String errorMsg = "Error in ChatServer.startserver: " + e.getMessage();
            SystemLogger.systemLogger.severe(errorMsg);

        }finally {
            stopServer();
        }

    }

    @Override
    public void stopServer() {
        if(!isOnline) return; //If server is already shutting down, return.

        io.put("Stopping server");
        isOnline = false;
        users.clear();
        messages.clear();

        try {
            if(serverSocket != null) {
                serverSocket.close();
            }

        }catch(IOException e) {
            String errorMsg = "Failed to close server socket in ChatServer.stopServer(): " + e.getMessage();
            SystemLogger.systemLogger.severe(errorMsg);
        }finally {
            System.exit(0);
        }


    }

    @Override
    public void addMessageToQueue(Message message) {
        messages.offer(message);

    }

    @Override
    public void removeUser(User user) {
        users.removeUser(user);
    }


    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void registerCommands() {
        //USER Commands
        commands.put("help", new HelpCommand(commands));
        commands.put("who", new WhoCommand(users));

        //ADMIN Commands
        commands.put("kick", new KickCommand(users));

        //SERVER Commands
        commands.put("addadmin", new AddAdminCommand(users));
        commands.put("removeadmin", new RemoveAdminCommand(users));
        commands.put("shutdown", new ShutdownCommand(this));


    }

    @Override
    public Command getCommand(String name) {
        return commands.get(name);
    }

}

