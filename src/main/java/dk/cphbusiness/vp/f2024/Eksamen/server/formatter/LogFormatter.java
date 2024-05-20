package dk.cphbusiness.vp.f2024.Eksamen.server.formatter;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFormatter extends Formatter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(dateFormat.format(new Date(record.getMillis())))
                .append(" [")
                .append(record.getLevel())
                .append("] ")
                .append(formatMessage(record))
                .append(System.lineSeparator());
        return builder.toString();
    }
}