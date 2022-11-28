package com.thecrownstudios.minestomlauncher.command.minestom;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.identity.Identity.nil;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

public class MinestomDefaultCommand extends Command {

	public MinestomDefaultCommand() {
		super("minestom");

		addSubcommand(new MinestomInfoCommand());
		addSubcommand(new MinestomShutdownCommand());
		addSubcommand(new MinestomVersionCommand());

		setDefaultExecutor(this::usage);
	}

	private void usage(@NotNull CommandSender sender, @NotNull CommandContext context) {
		var commandContext = context.getInput();

		sender.sendMessage(nil(), text()
				.append(newline())
				.append(text("Command usage:", color(140, 160, 160)))
				.append(newline())
				.append(text("\u25AA ", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
				.append(text("persona ", NamedTextColor.GRAY))
				.append(text("<subcommand>", NamedTextColor.AQUA))
		);

		Sound sound = Sound.sound(
				Key.key("minecraft", "block.note_block.chime"),
				Sound.Source.VOICE,
				2.0F, 1.0F
		);

		//sender.playSound(sound, Sound.Emitter.self());
	}

}