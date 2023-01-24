package com.thecrownstudios.minestomlauncher.util;

import com.thecrownstudios.minestomlauncher.MinestomData;
import com.thecrownstudios.minestomlauncher.MinestomLauncher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.format.TextColor.color;

public final class MessageUtil {

	private MessageUtil() {}

	private static final DecimalFormat decimalFormat = new DecimalFormat( "##0,000" );

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
			.append(text(" 1.2", NamedTextColor.GRAY, TextDecoration.ITALIC))
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

	public static @NotNull Component configMessage(long startMillis,
												   @NotNull FileResult result,
												   @NotNull MinestomData.Network networkData,
												   @NotNull MinestomData.Server serverData,
												   @NotNull MinestomData.Proxy proxyData)
	{
		TextComponent.Builder textBuilder = text();

		textBuilder
				.append(text("Server launch results: "))
				.append(newline())
				.append(newline());

		textBuilder
				.append(ARROW_COMPONENT)
				.append(text("config location: ", NamedTextColor.GRAY))
				.append(text(MinestomLauncher.CONFIG_LOCATION))
				.append(text(", ", NamedTextColor.GRAY))
				.append(configResultMessage(result))
				.append(newline());

		textBuilder
				.append(ARROW_COMPONENT)
				.append(text("server address: ", NamedTextColor.GRAY))
				.append(text(networkData.ip()))
				.append(text(":", NamedTextColor.GRAY))
				.append(text(networkData.port()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(proxyResultMessage(proxyData))
				.append(newline());

		textBuilder
				.append(ARROW_COMPONENT)
				.append(text("extensions: ", NamedTextColor.GRAY))
				.append(text(MinecraftServer.getExtensionManager().getExtensions().size()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(text("instances: ", NamedTextColor.GRAY))
				.append(text(MinecraftServer.getInstanceManager().getInstances().size()))
				.append(newline());

		textBuilder
				.append(ARROW_COMPONENT)
				.append(text("tps: ", NamedTextColor.GRAY))
				.append(text(serverData.ticksPerSecond()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(text("chunk distance: ", NamedTextColor.GRAY))
				.append(text(serverData.chunkViewDistance()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(text("entity distance: ", NamedTextColor.GRAY))
				.append(text(serverData.entityViewDistance()))
				.append(newline());

		if (MinecraftServer.getInstanceManager().getInstances().size() == 0) {
			textBuilder
					.append(newline())
					.append(text(" ! ", NamedTextColor.GOLD))
					.append(text("There are no registered instances,", NamedTextColor.YELLOW))
					.append(newline())
					.append(text("   you won't be able to join the server.", NamedTextColor.YELLOW))
					.append(newline());
		}

		textBuilder
				.append(newline())
				.append(text(" Server started in ", NamedTextColor.GRAY))
				.append(text(decimalFormat.format(System.currentTimeMillis() - startMillis)))
				.append(text("s", NamedTextColor.GRAY))
				.append(newline());

		return textBuilder.build();
	}

	public static @NotNull Component malformedConfigMessage() {
		return text()
				.append(newline())
				.append(newline())
				.append(text(" ! ", NamedTextColor.DARK_RED))
				.append(text("Error while launching the Minestom server:", NamedTextColor.RED))
				.append(newline())

				.append(newline())
				.append(text("   The configuration file is malformed, for security", NamedTextColor.RED))
				.append(newline())
				.append(text("   and logistic reason the server will automatically", NamedTextColor.RED))
				.append(newline())
				.append(text("   stop in 5 seconds...", NamedTextColor.RED))
				.append(newline())
				.append(newline())

				.build();
	}

	private static @NotNull Component configResultMessage(@NotNull FileResult result) {
		return switch (result) {
			case EXISTING ->	text("loaded from existing file");
			case CREATED ->		text("missing file, created a new one");
			case MALFORMED ->	empty();
		};
	}

	private static @NotNull Component proxyResultMessage(@NotNull MinestomData.Proxy proxyData) {
		if (proxyData.enabled()) {
			String proxyType = proxyData.type();

			if (proxyType.equalsIgnoreCase("velocity")) {
				return text("under velocity proxy");
			} else if (proxyType.equalsIgnoreCase("bungeecord")) {
				return text("under bungeecord proxy");
			}
		}

		return text("under no proxy");
	}

}