package com.thecrownstudios.minestomlauncher.console;

import java.util.regex.Pattern;
import net.minestom.server.MinecraftServer;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public record MinecraftCommandHighlighter(
        MinecraftServer server,
        StyleColor[] colors
) implements Highlighter {

    @Override
    public AttributedString highlight(final LineReader reader, final String buffer) {
        /*
        final AttributedStringBuilder builder = new AttributedStringBuilder();
        final StringReader stringReader = Util.prepareStringReader(buffer);
        //MinecraftServer.getCommandManager().getDispatcher().parse()
        final ParseResults<CommandSourceStack> results = this.server.getCommands().getDispatcher().parse(stringReader, this.server.createCommandSourceStack());

        int pos = 0;
        if (buffer.startsWith("/")) {
            builder.append("/", AttributedStyle.DEFAULT);
            pos = 1;
        }

        int colorIndex = -1;
        for (final ParsedCommandNode<CommandSourceStack> node : results.getContext().getLastChild().getNodes()) {
            if (node.getRange().getStart() >= buffer.length()) {
                break;
            }

            final int start = node.getRange().getStart();
            final int end = Math.min(node.getRange().getEnd(), buffer.length());

            if (node.getNode() instanceof LiteralCommandNode) {
                builder.append(buffer.substring(pos, start), AttributedStyle.DEFAULT);
                builder.append(buffer.substring(start, end), AttributedStyle.DEFAULT);
            } else {
                if (++colorIndex >= this.colors.length) {
                    colorIndex = 0;
                }
                builder.append(buffer.substring(pos, start), AttributedStyle.DEFAULT);
                builder.append(buffer.substring(start, end), AttributedStyle.DEFAULT.foreground(this.colors[colorIndex].index()));
            }
            pos = end;
        }

        if (pos < buffer.length()) {
            builder.append(buffer.substring(pos), AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
        }

        return builder.toAttributedString();
        */
        return new AttributedString("highlight");
    }

    @Override
    public void setErrorPattern(final Pattern errorPattern) {
    }

    @Override
    public void setErrorIndex(final int errorIndex) {
    }

    /**
     * Mirrors {@link org.jline.utils.AttributedStyle} color constants.
     */
    public enum StyleColor {
        BLACK(0),
        RED(1),
        GREEN(2),
        YELLOW(3),
        BLUE(4),
        MAGENTA(5),
        CYAN(6),
        WHITE(7);

        private final int index;

        StyleColor(final int index) {
            this.index = index;
        }

        public int index() {
            return this.index;
        }
    }

}