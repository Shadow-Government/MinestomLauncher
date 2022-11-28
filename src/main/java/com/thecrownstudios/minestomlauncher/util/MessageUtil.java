package com.thecrownstudios.minestomlauncher.util;

import com.thecrownstudios.minestomlauncher.MinestomLauncher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.format.TextColor.color;

public final class MessageUtil {

	private MessageUtil() {}

	public static final String GITHUB_URL = "https://github.com/The-Crown-Studios/MinestomLauncher";
	public static final String MINESTOM_COMMIT_URL = "https://github.com/Minestom/Minestom/commit/" + MinestomLauncher.COMMIT;
	public static final String MINESTOM_MINECRAFT_VERSION_NAME_URL = "https://github.com/Minestom/Minestom/blob/master/src/main/java/net/minestom/server/MinecraftServer.java#L48";
	public static final String MINESTOM_MINECRAFT_VERSION_PROTOCOL_URL = "https://github.com/Minestom/Minestom/blob/master/src/main/java/net/minestom/server/MinecraftServer.java#L49";

	public static final char CUBE		=	'\u25A0';
	public static final char BIG_CUBE	=	'\u2588';
	public static final char ARROW		=	'\u25B8';

	public static final Component CUBE_COMPONENT = text()
			.append(text(CUBE, NamedTextColor.RED, TextDecoration.BOLD))
			.build();

	public static final Component CUBE_COMPONENT_LINE = text()
			.append(CUBE_COMPONENT)
			.append(space())
			.append(CUBE_COMPONENT)
			.append(space())
			.append(CUBE_COMPONENT)
			.build();

	public static final Component BIG_CUBE_COMPONENT = text()
			.append(text(BIG_CUBE, color(255, 113, 116)))
			.build();

	public static final Component BIG_CUBE_COMPONENT_LINE = text()
			.append(BIG_CUBE_COMPONENT)
			.append(BIG_CUBE_COMPONENT)
			.append(BIG_CUBE_COMPONENT)
			.build();

	public static final Component ARROW_COMPONENT = text()
			.append(text(" "))
			.append(text(ARROW, NamedTextColor.GRAY, TextDecoration.BOLD))
			.append(space())
			.build();

	public static final Component LAUNCH_MESSAGE = text()
			.append(text("Starting Minestom launcher."))
			.append(newline())
			.append(newline())

			.append(CUBE_COMPONENT_LINE)
			.append(newline())
			.append(CUBE_COMPONENT_LINE)
			.append(text("   MINESTOM LAUNCHER"))
			.append(text(" 1.0", NamedTextColor.GRAY, TextDecoration.ITALIC))
			.append(newline())
			.append(CUBE_COMPONENT_LINE)

			.append(newline())
			.append(newline())

			.append(ARROW_COMPONENT)
			.append(text("os info: ", NamedTextColor.GRAY))
			.append(text(System.getProperty("os.name")))
			.append(text(", ", NamedTextColor.GRAY))
			.append(text(System.getProperty("os.version")))
			.append(text(" - ", NamedTextColor.GRAY))
			.append(text(System.getProperty("os.arch")))
			.append(newline())

			.append(ARROW_COMPONENT)
			.append(text("java info: ", NamedTextColor.GRAY))
			.append(text(System.getProperty("java.version")))
			.append(text(" - ", NamedTextColor.GRAY))
			.append(text(System.getProperty("java.vendor")))
			.append(text(", ", NamedTextColor.GRAY))
			.append(text(System.getProperty("java.vendor.url")))
			.append(newline())

			.append(ARROW_COMPONENT)
			.append(text("commit: ", NamedTextColor.GRAY))
			.append(text(MinestomLauncher.COMMIT))
			.append(newline())

			.append(ARROW_COMPONENT)
			.append(text("version: ", NamedTextColor.GRAY))
			.append(text(MinestomLauncher.MINECRAFT_VERSION_NAME))
			.append(text(" - ", NamedTextColor.GRAY))
			.append(text(MinestomLauncher.MINECRAFT_VERSION_PROTOCOL))
			.append(newline())

			.build();

}