package com.thecrownstudios.minestomlauncher.terminal;

import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;

public class ConsoleComponentDeserializer {

    // micro optimization:
    // with this we can reset text style using this string ref
    // instead of allocating a new string everytime.
    private static final String resetTemplate = "\033[0m";

    // micro optimization:
    // with this, String.format will always use this ref
    // instead of allocating a new string everytime.
    private static final String rgbTemplate = "\033[38;2;%d;%d;%dm";

    // \033[38;2;<red>;<green>;<blue>m
    public static String deserialize(ComponentLike componentLike) {
        return switch(componentLike) {
            case TextComponent text -> {
                var children = text.children();
                TextColor color = text.color();
                String ansiColor = color != null ? convertToAnsi(color) : "";
                String content = text.content();

                if (children.isEmpty()) {
                    yield ansiColor + content + resetTemplate;
                } else {
                    StringBuilder builder = new StringBuilder();
                    builder.append(ansiColor).append(content).append(resetTemplate);
                    //System.out.println("DESERIALIZE | CHILDREN: " + "'" + (ansiColor + content + resetTemplate) + "'");
                    yield childrenDeserializer(builder, children);
                }
            }
            case TextComponent.Builder textBuilder -> {
                var component = textBuilder.build();
                yield deserialize(component);
            }
            default -> "Not supported value: " + componentLike;
        };
    }

    public static void deserialize(StringBuilder builder, ComponentLike componentLike) {
        switch(componentLike) {
            case TextComponent text -> {
                var children = text.children();
                TextColor color = text.color();
                String ansiColor = color != null ? convertToAnsi(color) : "";
                String content = text.content();

                if (children.isEmpty()) {
                    builder.append(ansiColor + content + resetTemplate);
                } else {
                    //System.out.println("DESERIALIZE | BUILDER | CHILDREN: " + "'" + (ansiColor + content + resetTemplate) + "'");
                    builder.append(ansiColor + content + resetTemplate);
                    childrenDeserializer(builder, children);
                }
            }
            case TextComponent.Builder textBuilder -> {
                var component = textBuilder.build();
                builder.append(deserialize(component));
            }
            default -> builder.append("Not supported value: ").append(componentLike);
        };
    }

    private static String childrenDeserializer(List<Component> children) {
        StringBuilder builder = new StringBuilder();

        for (ComponentLike child : children) {
            deserialize(builder, child);
        }

        return builder.toString();
    }

    private static String childrenDeserializer(StringBuilder builder, List<Component> children) {
        for (ComponentLike child : children) {
            deserialize(builder, child);
        }

        return builder.toString();
    }

    private static String convertToAnsi(TextColor color) {
        return String.format(rgbTemplate, color.red(), color.green(), color.blue());
    }

}