package com.thecrownstudios.minestomlauncher.console;

import org.jline.reader.LineReader;

public record ConsoleState(
        LineReader lineReader,
        DelegatingCompleter completer,
        DelegatingHighlighter highlighter
) {
}