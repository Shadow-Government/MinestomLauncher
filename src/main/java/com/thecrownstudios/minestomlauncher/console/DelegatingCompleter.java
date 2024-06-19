package com.thecrownstudios.minestomlauncher.console;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public final class DelegatingCompleter implements Completer {
    private @Nullable Completer delegate;

    @Override
    public void complete(final LineReader reader, final ParsedLine line, final List<Candidate> candidates) {
        if (this.delegate != null) {
            this.delegate.complete(reader, line, candidates);
        }
    }

    public void delegateTo(final Completer completer) {
        this.delegate = completer;
    }
}