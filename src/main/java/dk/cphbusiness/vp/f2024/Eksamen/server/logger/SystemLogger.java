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

public class SystemLogger {
    public static final Logger systemLogger = Logger.getLogger(ChatServerImpl.class.getName());
    private final ChatServer server;
    public SystemLogger(ChatServer server, String logDir) {
        this.server = server;
        configureLogger(logDir);
    }


    private void configureLogger(String filepath) {

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
            FileHandler fileHandler = getFileHandler(filepath, logFormat);
            systemLogger.addHandler(fileHandler);

            // Setup console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(logFormat);
            systemLogger.addHandler(consoleHandler);

        } catch (IOException e) {
            System.err.println("Failed to set up logger: " + e.getMessage());
            server.stopServer();
        }
    }

    private static FileHandler getFileHandler(String logDirectory, LogFormatter logFormat) throws IOException {
        //Add a date to the formatter, for file naming
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = formatter.format(date);

        String logFilePath = logDirectory + "\\" + dateStr + "-system.log";

        //Check if logDirectory exists, if not create one.
        File logDir = new File(logDirectory);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        //Setup the filehandler to append messages and set the formatter to LogFormatter
        FileHandler fileHandler = new FileHandler(logFilePath, true);
        fileHandler.setFormatter(logFormat);
        return fileHandler;
    }



}
