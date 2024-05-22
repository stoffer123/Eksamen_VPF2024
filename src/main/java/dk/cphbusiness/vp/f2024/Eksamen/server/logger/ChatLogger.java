package dk.cphbusiness.vp.f2024.Eksamen.server.logger;

import dk.cphbusiness.vp.f2024.Eksamen.server.interfaces.ChatServer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ChatLogger {
    public static final Logger chatLogger = Logger.getLogger("ChatLogger");
    private final String logDir;
    private final ChatServer server;

    public ChatLogger(ChatServer server, String logDir) {
        this.logDir = logDir;
        this.server = server;
        configureLogger();
    }

    private void configureLogger() {
        try {
            // Disable parent handlers to prevent to prevent sharing handler with SystemLogger
            chatLogger.setUseParentHandlers(false);

            //use LogFormatter
            LogFormatter logFormat = new LogFormatter();

            // Setup file handler
            FileHandler fileHandler = getFileHandler(logDir, logFormat);
            chatLogger.addHandler(fileHandler);


        } catch (IOException e) {
            System.err.println("Failed to set up chat logger: " + e.getMessage());
            server.stopServer();

        }
    }

    private FileHandler getFileHandler(String logDirectory, LogFormatter logFormat) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = formatter.format(date);

        String logFilePath = logDirectory + "\\" + dateStr + "-chat.log";

        File logDir = new File(logDirectory);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        FileHandler fileHandler = new FileHandler(logFilePath, true);
        fileHandler.setFormatter(logFormat);
        return fileHandler;
    }
}