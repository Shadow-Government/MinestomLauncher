package com.thecrownstudios.minestomlauncher;

import com.thecrownstudios.minestomlauncher.command.ShutdownCommand;
import com.thecrownstudios.minestomlauncher.util.FileResult;
import com.thecrownstudios.minestomlauncher.util.FileUtil;
import com.thecrownstudios.minestomlauncher.util.ObjectTriple;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.lan.OpenToLAN;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.time.Duration;

import static com.thecrownstudios.minestomlauncher.util.MessageUtil.ARROW_COMPONENT;
import static com.thecrownstudios.minestomlauncher.util.MessageUtil.LAUNCH_MESSAGE;
import static net.kyori.adventure.text.Component.*;

public final class MinestomLauncher {

	public static final String COMMIT					=	"e5d0a43ba42c7023a93e8e33356909e614783ac7";
	public static final String COMMIT_REDUCED			=	"e5d0a43ba4";
	public static final String VERSION_NAME				=	"1.0";
	public static final String MINECRAFT_VERSION_NAME	=	"1.19.2";
	public static final int MINECRAFT_VERSION_PROTOCOL	=	760;

	private static final ComponentLogger LOGGER = ComponentLogger.logger(MinestomLauncher.class);
	private static final String CONFIG_LOCATION = System.getProperty("config.location", "server.json");

	public MinestomLauncher() {}

	public void init() {
		LOGGER.info(LAUNCH_MESSAGE);

		loadConfiguration();
	}

	private void start(long startMillis, @NotNull FileResult result, @NotNull MinestomData minestomData) {
		MinestomData.Network networkData	=	minestomData.networkData();
		MinestomData.Proxy proxyData		=	minestomData.proxyData();
		MinestomData.Server serverData		=	minestomData.serverData();

		System.setProperty("minestom.tps", String.valueOf(serverData.ticksPerSecond()));
		System.setProperty("minestom.chunk-view-distance", String.valueOf(serverData.chunkViewDistance()));
		System.setProperty("minestom.entity-view-distance", String.valueOf(serverData.entityViewDistance()));

		MinecraftServer minecraftServer		=	MinecraftServer.init();

		if (networkData.openToLan()) {
			OpenToLAN.open();
		}

		if (serverData.optifineSupport()) {
			OptifineSupport.enable();
		}

		if (proxyData.enabled()) {
			String proxyType = proxyData.type();

			if (proxyType.equalsIgnoreCase("velocity")) {
				VelocityProxy.enable(proxyData.secret());
			} else if (proxyType.equalsIgnoreCase("bungeecord")) {
				BungeeCordProxy.enable();
			}
		} else if (serverData.onlineMode()) {
			MojangAuth.init();
		}

		if (serverData.benchmark()) {
			MinecraftServer.getBenchmarkManager().enable(Duration.of(10, TimeUnit.SECOND));
		}

		CommandManager commandManager = MinecraftServer.getCommandManager();
		commandManager.register(new ShutdownCommand());
		//commandManager.register(new MinestomDefaultCommand());
		commandManager.setUnknownCommandCallback((sender, command) -> sender.sendMessage(text("Unknown command", NamedTextColor.RED)));

		MinecraftServer.getSchedulerManager().buildShutdownTask(() -> System.out.println("Thanks for using Minestom Launcher."));

		minecraftServer.start(networkData.ip(), networkData.port());

		LOGGER.info(message(startMillis, result, networkData, serverData, proxyData));
	}

	private void shutdown() {
		LOGGER.info(malformedConfigMessage());

		try {
			Thread.sleep(5000);
		} catch (IllegalArgumentException | InterruptedException exc) {
			throw new RuntimeException(exc);
		}
	}

	private void loadConfiguration() {
		long startMillis = System.currentTimeMillis();
		ObjectTriple<FileResult, MinestomData, Exception> triple = FileUtil.loadMinestomDataFromFile(CONFIG_LOCATION);

		switch(triple.left()) {
			case CREATED, EXISTING -> {
				start(startMillis, triple.left(), triple.mid());
			}
			case MALFORMED -> {
				shutdown();
			}
		}
	}

	private @NotNull Component message(
			long startMillis,
			@NotNull FileResult result,
			@NotNull MinestomData.Network networkData,
			@NotNull MinestomData.Server serverData,
			@NotNull MinestomData.Proxy proxyData)
	{
		DecimalFormat decimalFormat = new DecimalFormat( "#,###" );

		return text()
				.append(text("Server launch results: "))
				.append(newline())
				.append(newline())

				.append(ARROW_COMPONENT)
				.append(text("config location: ", NamedTextColor.GRAY))
				.append(text(CONFIG_LOCATION))
				.append(text(", ", NamedTextColor.GRAY))
				.append(switch (result) {
					case EXISTING -> text("loaded from existing file");
					case CREATED -> text("missing file, created a new one");
					case MALFORMED -> empty();
				})
				.append(newline())

				.append(ARROW_COMPONENT)
				.append(text("server address: ", NamedTextColor.GRAY))
				.append(text(networkData.ip()))
				.append(text(":", NamedTextColor.GRAY))
				.append(text(networkData.port()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(retrieveProxyMessage(proxyData))
				.append(newline())

				.append(ARROW_COMPONENT)
				.append(text("tps: ", NamedTextColor.GRAY))
				.append(text(serverData.ticksPerSecond()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(text("chunk distance: ", NamedTextColor.GRAY))
				.append(text(serverData.chunkViewDistance()))
				.append(text(", ", NamedTextColor.GRAY))
				.append(text("entity distance: ", NamedTextColor.GRAY))
				.append(text(serverData.entityViewDistance()))
				.append(newline())

				.append(newline())
				.append(text(" Server started in ", NamedTextColor.GRAY))
				.append(text(decimalFormat.format(System.currentTimeMillis() - startMillis)))
				.append(text("s", NamedTextColor.GRAY))
				.append(newline())

				.build();
	}

	private @NotNull Component malformedConfigMessage() {
		return text()
				.append(text("Error while launching the Minestom server:"))
				.append(newline())

				.append(newline())
				.append(text(" The configuration file is malformed, for security", NamedTextColor.RED))
				.append(newline())
				.append(text(" and logistic reason the server will automatically", NamedTextColor.RED))
				.append(newline())
				.append(text(" stop in 5 seconds...", NamedTextColor.RED))
				.append(newline())
				.append(newline())

				.build();
	}

	private @NotNull Component retrieveProxyMessage(@NotNull MinestomData.Proxy proxyData) {
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