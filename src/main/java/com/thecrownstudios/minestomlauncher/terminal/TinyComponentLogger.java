package com.thecrownstudios.minestomlauncher.terminal;

import net.kyori.adventure.text.ComponentLike;
import org.tinylog.Logger;
import org.tinylog.Supplier;

public interface TinyComponentLogger {

    static void info(ComponentLike component) {
        var result = ConsoleComponentDeserializer.deserialize(component);

        Logger.info(result);
    }

    static void info(Supplier<ComponentLike> message) {
        var result = ConsoleComponentDeserializer.deserialize(message.get());

        Logger.info(result);
    }

}