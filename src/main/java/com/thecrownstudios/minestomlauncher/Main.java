package com.thecrownstudios.minestomlauncher;

import static net.kyori.adventure.text.Component.text;

public final class Main {

	public static void main(String[] args) {
		//TinyComponentLogger.info(text("COMPONENT TEST", TextColor.color(196, 163, 73)));
		//TinyComponentLogger.info(Component.space().append(text("suca", TextColor.color(100, 240, 90))));
		//TinyComponentLogger.info(text("pupu", TextColor.color(255, 255, 0)).append(text("suca", TextColor.color(240, 100, 90))));
		//new Terminal().start();

		new MinestomLauncher().init();
	}

}