package com.thecrownstudios.minestomlauncher.command.minestom;

import com.thecrownstudios.minestomlauncher.util.MessageUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;

import static net.kyori.adventure.identity.Identity.nil;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;

public class MinestomInfoCommand extends Command {

	public MinestomInfoCommand() {
		super("info");

		setDefaultExecutor(this::execute);
	}

	private void execute(CommandSender sender, CommandContext context) {
		var commandContext = context.getInput();

		sender.sendMessage(nil(), text()
				.append(newline())
				.append(MessageUtil.LAUNCH_MESSAGE)
		);

		Sound sound = Sound.sound(
				Key.key("minecraft", "block.note_block.chime"),
				Sound.Source.VOICE,
				2.0F, 1.0F
		);

		sender.playSound(sound, Sound.Emitter.self());
	}

}