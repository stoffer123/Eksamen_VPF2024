package dk.cphbusiness.vp.f2024.Eksamen.server.logger;

import dk.cphbusiness.vp.f2024.Eksamen.server.impl.ChatServerImpl;
import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ServerLogger {
    public static final Logger logger = Logger.getLogger(ChatServerImpl.class.getName());
    private ChatServer server;
    private String logDir;
    public ServerLogger(ChatServer server, String logDir) {
        this.server = server;
        configureLogger(logDir);
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
            server.stopServer();
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
