package com.thecrownstudios.minestomlauncher.console;

import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Completer;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

public final class ConsoleSetup {

    private ConsoleSetup() {
    }

    public static final String APP_NAME = "Minestom Server";
    public static final String LOG_PATTERN = "%highlight{[%d{HH:mm:ss} %level] [%t]: [%logger{1}]}{FATAL=red, ERROR=red, WARN=yellow, INFO=default, DEBUG=yellow, TRACE=blue} %paperMinecraftFormatting{%msg}%n";


    private static LineReader buildLineReader(
            final Completer completer,
            final Highlighter highlighter
    ) {
        return LineReaderBuilder.builder()
                .appName(APP_NAME)
                .variable(LineReader.HISTORY_FILE, Paths.get(".console_history"))
                .completer(completer)
                .highlighter(highlighter)
                .option(LineReader.Option.INSERT_TAB, false)
                .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                .option(LineReader.Option.COMPLETE_IN_WORD, true)
                .build();
    }

    public static ConsoleState init(
            final @Nullable Object remapper,
            final String logPattern
    ) {
        final DelegatingCompleter delegatingCompleter = new DelegatingCompleter();
        final DelegatingHighlighter delegatingHighlighter = new DelegatingHighlighter();
        final LineReader lineReader = buildLineReader(
                delegatingCompleter,
                delegatingHighlighter
        );

        final ConsoleAppender consoleAppender = new ConsoleAppender(
                lineReader,
                LOG_PATTERN,
                null
                //remapper != null ? new RemappingRewriter(remapper) : null
        );
        consoleAppender.start();

        final Logger logger = (Logger) LogManager.getRootLogger();
        final LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        final LoggerConfig loggerConfig = loggerContext.getConfiguration().getLoggerConfig(logger.getName());

        // replace SysOut appender with ConsoleAppender
        loggerConfig.removeAppender("SysOut");
        loggerConfig.addAppender(consoleAppender, loggerConfig.getLevel(), null);
        loggerContext.updateLoggers();

        return new ConsoleState(lineReader, delegatingCompleter, delegatingHighlighter);
    }

}