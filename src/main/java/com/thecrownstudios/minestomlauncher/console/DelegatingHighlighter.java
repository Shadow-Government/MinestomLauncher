package com.thecrownstudios.minestomlauncher.console;

import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public final class DelegatingHighlighter implements Highlighter {
    private @Nullable Highlighter delegate;

    @Override
    public AttributedString highlight(final LineReader reader, final String buffer) {
        if (this.delegate != null) {
            return this.delegate.highlight(reader, buffer);
        }
        final AttributedStringBuilder builder = new AttributedStringBuilder();
        builder.append(buffer, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
        return builder.toAttributedString();
    }

    @Override
    public void setErrorPattern(final Pattern errorPattern) {
        if (this.delegate != null) {
            this.delegate.setErrorPattern(errorPattern);
        }
    }

    @Override
    public void setErrorIndex(final int errorIndex) {
        if (this.delegate != null) {
            this.delegate.setErrorIndex(errorIndex);
        }
    }

    public void delegateTo(final Highlighter highlighter) {
        this.delegate = highlighter;
    }
}