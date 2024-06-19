package com.thecrownstudios.minestomlauncher.console;

import java.util.List;
import java.util.Optional;
//import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.minestom.server.MinecraftServer;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

public record MinecraftCommandCompleter(MinecraftServer server) implements Completer {

    @Override
    public void complete(final LineReader reader, final ParsedLine line, final List<Candidate> candidates) {
        /*
        final StringReader stringReader = Util.prepareStringReader(line.line());
        final ParseResults<CommandSourceStack> results = this.server.getCommands().getDispatcher().parse(stringReader, this.server.createCommandSourceStack());
        final CompletableFuture<Suggestions> suggestionsFuture = this.server.getCommands().getDispatcher().getCompletionSuggestions(results, line.cursor());
        final Suggestions suggestions = suggestionsFuture.join();

        for (final Suggestion suggestion : suggestions.getList()) {
            final String suggestionText = suggestion.getText();
            if (suggestionText.isEmpty()) {
                continue;
            }

            final @Nullable String description = Optional.ofNullable(suggestion.getTooltip())
                    .map(tooltip -> {
                        final Component tooltipComponent = ComponentUtils.fromMessage(tooltip);
                        return tooltipComponent.equals(Component.empty()) ? null : tooltipComponent.asComponent();
                    })
                    .map(adventure -> ANSIComponentSerializer.ansi().serialize(adventure))
                    .orElse(null);

            candidates.add(new Candidate(
                    suggestionText,
                    suggestionText,
                    null,
                    description,
                    null,
                    null,
                    false
            ));
        }
        */
    }

}