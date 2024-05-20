package dk.cphbusiness.vp.f2024.Eksamen.server.impl;

import dk.cphbusiness.vp.f2024.Eksamen.server.formatter.LogFormatter;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.*;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ChatServerImpl implements ChatServer {
    public static final Logger logger = Logger.getLogger(ChatServerImpl.class.getName());

    private final int port;
    private final TextIO io;
    private ServerSocket serverSocket;
    private final UserList users;
    private final BlockingQueue<Message> messages;
    private boolean isOnline;
    private final String logFilePath;



    public ChatServerImpl(int port, TextIO io, String logFilePath) {
        this.port = port;
        this.io = io;
        users = new UserListImpl(this, io);
        messages = new LinkedBlockingQueue<>(100);
        isOnline = true;
        this.logFilePath = logFilePath;

        configureLogger(this.logFilePath);
        logger.info("Server started on port " + port);

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

            }
        }catch(IOException e) {
            String errorMsg = "Error in ChatServer.startserver: " + e.getMessage();
            logger.severe(errorMsg);

        }finally {
            stopServer();
        }
    }

    @Override
    public void stopServer() {
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
            logger.severe(errorMsg);
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


    private void configureLogger(String filepath) {
        String logFilepath = filepath;

        try {
            // Remove default console handler if present
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }

            // Setup custom log formatter
            LogFormatter logFormat = new LogFormatter();

            // Setup file handler
            FileHandler fileHandler = getFileHandler(logFilepath, logFormat);
            logger.addHandler(fileHandler);

            // Setup console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(logFormat);
            logger.addHandler(consoleHandler);

        } catch (IOException e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
            stopServer();
        }
    }

    private static FileHandler getFileHandler(String logDirectory, LogFormatter logFormat) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = formatter.format(date);

        String logFilePath = logDirectory + "\\" + dateStr + "-server.log";

        File logDir = new File(logDirectory);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        FileHandler fileHandler = new FileHandler(logFilePath, true);
        fileHandler.setFormatter(logFormat);
        return fileHandler;
    }

}

