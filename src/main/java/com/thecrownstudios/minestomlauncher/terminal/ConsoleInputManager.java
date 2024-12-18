package com.thecrownstudios.minestomlauncher.terminal;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ConsoleSender;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class ConsoleInputManager {
    private final CommandManager commandManager;
    private Terminal terminal;
    private LineReader lineReader;

    public ConsoleInputManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        initializeConsole();
        startInputThread();
    }

    private void initializeConsole() {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();

            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startInputThread() {
        Thread inputThread = new Thread(() -> {
            while (true) {
                try {
                    // Legge l'input
                    String input = lineReader.readLine("> ");

                    this.commandManager.executeServerCommand(input);

                    // commandManager.handleCommand(input);
                }
                catch (UserInterruptException e) {
                    // nothing
                    e.printStackTrace();
                }
                catch (EndOfFileException e) {
                    // e.printStackTrace();
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    private void showCustomSuggestions(String input) {
        String suggestionColor = "\033[36m";  // Ciano
        String resetColor = "\033[0m";

        // Pulisci la riga corrente senza toccare il prompt
        terminal.writer().print("\r> "); // Ripristina il prompt

        // Trova suggerimenti basati sull'input
        List<String> suggestions = findSuggestions(input);

        // Se ci sono suggerimenti, mostali
        if (!suggestions.isEmpty()) {
            // Mostra fino a 3 suggerimenti
            for (int i = 0; i < Math.min(3, suggestions.size()); i++) {
                terminal.writer().println(suggestionColor +
                        "Suggerimento " + (i + 1) + ": " + suggestions.get(i) + resetColor);
            }
        } else {
            // Suggerimenti predefiniti se non trova corrispondenze
            terminal.writer().println(suggestionColor + "Suggerimento 1: Prova a scrivere un comando" + resetColor);
            terminal.writer().println(suggestionColor + "Suggerimento 2: Digita 'help' per vedere i comandi" + resetColor);
            terminal.writer().println(suggestionColor + "Suggerimento 3: Comandi disponibili sono limitati" + resetColor);
        }

        // Ripristina il prompt e l'input (senza sovrascrivere tutto)
        terminal.writer().print("> " + input);
        terminal.writer().flush();  // Forza la stampa immediata
    }

    private List<String> findSuggestions(String input) {
        return List.of("prova1", "prova2", "prova3");
        /*
        return commandManager.getAvailableCommands().stream()
                .filter(cmd -> cmd.toLowerCase().contains(input.toLowerCase()))
                .collect(Collectors.toList());
         */
    }
}
