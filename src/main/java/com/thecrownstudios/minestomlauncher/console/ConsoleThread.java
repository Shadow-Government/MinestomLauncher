package com.thecrownstudios.minestomlauncher.console;

import net.minestom.server.MinecraftServer;
import net.minestom.server.ServerProcess;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;

public final class ConsoleThread extends Thread {

    private static final String TERMINAL_PROMPT = "> ";
    private static final String STOP_COMMAND = "stop";

    private final ServerProcess server;
    private final LineReader lineReader;

    public ConsoleThread(
            final ServerProcess server,
            final LineReader lineReader
    ) {
        super("Console thread");
        this.server = server;
        this.lineReader = lineReader;
    }

    @Override
    public void run() {
        MinecraftServer.LOGGER.info("Initialized Better Fabric Console console thread.");
        this.acceptInput();
    }

    private static boolean isRunning(final ServerProcess server) {
        //return !server.isStopped() && server.isRunning();
        return server.isAlive();
    }

    private void acceptInput() {
        while (isRunning(this.server)) {
            try {
                final String input = this.lineReader.readLine(TERMINAL_PROMPT).trim();
                if (input.isEmpty()) {
                    continue;
                }

                //this.server.handleConsoleInput(input, this.server.createCommandSourceStack());
                if (input.equals(STOP_COMMAND)) {
                    break;
                }
            } catch (final EndOfFileException | UserInterruptException ex) {
                //this.server.handleConsoleInput(STOP_COMMAND, this.server.createCommandSourceStack());
                break;
            }
        }
    }
}