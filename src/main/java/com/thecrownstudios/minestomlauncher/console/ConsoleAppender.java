package com.thecrownstudios.minestomlauncher.console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.LineReader;

final class ConsoleAppender extends AbstractAppender {
    private final LineReader lineReader;
    private final @Nullable RewritePolicy rewriter;

    ConsoleAppender(
            final LineReader lineReader,
            final String logPattern,
            final @Nullable RewritePolicy rewritePolicy
    ) {
        super(
                "Console",
                null,
                PatternLayout.newBuilder().withPattern(logPattern).build(),
                false,
                new Property[0]
        );
        this.lineReader = lineReader;
        this.rewriter = rewritePolicy;
    }

    private LogEvent rewrite(final LogEvent event) {
        return this.rewriter == null ? event : this.rewriter.rewrite(event);
    }

    @Override
    public void append(final LogEvent event) {
        if (this.lineReader.isReading()) {
            this.lineReader.callWidget(LineReader.CLEAR);
        }

        this.lineReader.getTerminal().writer().print(this.getLayout().toSerializable(this.rewrite(event)).toString());

        if (this.lineReader.isReading()) {
            this.lineReader.callWidget(LineReader.REDRAW_LINE);
            this.lineReader.callWidget(LineReader.REDISPLAY);
        }
        this.lineReader.getTerminal().writer().flush();
    }
}