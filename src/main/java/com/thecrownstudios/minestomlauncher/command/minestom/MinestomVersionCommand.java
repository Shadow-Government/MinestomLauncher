package com.thecrownstudios.minestomlauncher.command.minestom;

import com.thecrownstudios.minestomlauncher.MinestomLauncher;
import com.thecrownstudios.minestomlauncher.util.MessageUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.Git;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.thecrownstudios.minestomlauncher.util.MessageUtil.*;
import static net.kyori.adventure.identity.Identity.nil;
import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;

public class MinestomVersionCommand extends Command {

	public MinestomVersionCommand() {
		super("version");

		setDefaultExecutor(this::execute);
	}

	private void execute(@NotNull CommandSender sender, @NotNull CommandContext context) {
		if (sender instanceof Player player) {
			execute(player, context);
		} else if (sender instanceof ConsoleSender console) {
			execute(console, context);
		}
	}

	private void execute(@NotNull Player player, @NotNull CommandContext context) {
		var commandContext = context.getInput();

		player.sendMessage(nil(), text()
				.append(newline())

				.append(BIG_CUBE_COMPONENT_LINE.clickEvent(openUrl(GITHUB_URL)))
				.append(space())
				.append(space())
				.append(text("launcher: ", NamedTextColor.GRAY))
				.append(text(MinestomLauncher.LAUNCHER_VERSION_NAME)
						.hoverEvent(HoverEvent.showText(text("Open github project", NamedTextColor.GRAY)))
						.clickEvent(openUrl(GITHUB_URL))
				)

				.append(newline())

				.append(BIG_CUBE_COMPONENT_LINE.clickEvent(openUrl(GITHUB_URL)))
				.append(space())
				.append(space())
				.append(text("commit: ", NamedTextColor.GRAY))
				.append(text(Git.commit())
						.hoverEvent(HoverEvent.showText(text("Open commit URL", NamedTextColor.GRAY)))
						.clickEvent(openUrl(MINESTOM_COMMIT_URL))
				)

				.append(newline())

				.append(BIG_CUBE_COMPONENT_LINE.clickEvent(openUrl(GITHUB_URL)))
				.append(space())
				.append(space())
				.append(text("version: ", NamedTextColor.GRAY))
				.append(text(MinecraftServer.VERSION_NAME)
						.hoverEvent(HoverEvent.showText(text("Open minestom minecraft version name URL", NamedTextColor.GRAY)))
						.clickEvent(openUrl(MINESTOM_MINECRAFT_VERSION_NAME_URL))
				)
				.append(text(", ", NamedTextColor.GRAY))
				.append(text(MinecraftServer.PROTOCOL_VERSION)
						.hoverEvent(HoverEvent.showText(text("Open minestom minecraft version protocol URL", NamedTextColor.GRAY)))
						.clickEvent(openUrl(MINESTOM_MINECRAFT_VERSION_PROTOCOL_URL))
				)
		);

		Sound sound = Sound.sound(
				Key.key("minecraft", "block.note_block.chime"),
				Sound.Source.VOICE, 2.0F, 1.0F
		);

		player.playSound(sound, Sound.Emitter.self());
	}

	private void execute(@NotNull ConsoleSender sender, @NotNull CommandContext context) {
		var commandContext = context.getInput();

		sender.sendMessage(nil(), text()
				//.append(newline())
				.append(MessageUtil.LAUNCH_MESSAGE)
		);
	}

}