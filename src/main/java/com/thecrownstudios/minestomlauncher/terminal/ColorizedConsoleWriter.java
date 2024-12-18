package com.thecrownstudios.minestomlauncher.terminal;

import org.tinylog.Level;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.format.AdvancedMessageFormatter;
import org.tinylog.format.MessageFormatter;
import org.tinylog.writers.Writer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ColorizedConsoleWriter implements Writer {

    private final Map<String, String> properties;

    public ColorizedConsoleWriter(Map<String, String> properties) {
        System.out.println("ColorizedConsoleWriter initialized with properties: " + properties);
        this.properties = properties;
    }

    @Override
    public void write(LogEntry logEntry) throws Exception {
        String colorCode = getColorForLevel(logEntry.getLevel());
        String resetCode = "\033[0m";
        String message = logEntry.getMessage();

        // Recupera il formato dalla mappa delle properties
       //String format = properties.getOrDefault("format", "[{date: HH:mm:ss}] {level}: {message}");

        // Sostituisci i segnaposto con i valori del LogEntry
        String formattedMessage = "[{date: HH:mm:ss}] {level}: {message}"
                .replace("{date: HH:mm:ss}", logEntry.getTimestamp().toInstant().toString()) // Formatta la data come necessario
                .replace("{level}", logEntry.getLevel().toString())
                .replace("{thread}", logEntry.getThread().getName())
                .replace("{class-name}", logEntry.getClassName())
                .replace("{method}", logEntry.getMethodName())
                .replace("{message}", logEntry.getMessage());

        // Stampa il messaggio con i colori ANSI
        System.out.println(colorCode + formattedMessage + resetCode);

        //System.out.println(colorCode + message + resetCode);

        if (logEntry.getException() != null) {
            logEntry.getException().printStackTrace(System.out);
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws Exception {
        // Nessuna risorsa da chiudere in questo caso
    }

    @Override
    public Collection<LogEntryValue> getRequiredLogEntryValues() {
        return List.of(LogEntryValue.LEVEL, LogEntryValue.MESSAGE, LogEntryValue.EXCEPTION);
    }

    private String getColorForLevel(Level level) {
        return switch (level) {
            case ERROR -> "\033[38;2;255;0;0m"; // Rosso
            case WARN -> "\033[38;2;255;165;0m"; // Arancione
            case INFO -> "\033[38;2;0;255;0m"; // Verde
            case DEBUG -> "\033[38;2;0;0;255m"; // Blu
            case TRACE -> "\033[38;2;128;0;128m"; // Viola
            default -> "\033[38;2;255;255;255m"; // Bianco
        };
    }

}
