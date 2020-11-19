package me.sina.simplebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.sina.simplebot.commands.general.AboutCommand;
import me.sina.simplebot.commands.general.UserInfoCommand;
import me.sina.simplebot.commands.moderation.BanCommand;
import me.sina.simplebot.commands.moderation.KickCommand;
import me.sina.simplebot.config.ConfigManager;

public class Main {
	private static Bot BOT;
	private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(final String[] args) throws Exception {

		new Main();
	}

	public Main() {
		ConfigManager.update();
		try {
			BOT = new Bot();
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		getBot().registerCommands(new KickCommand(), new BanCommand(), new UserInfoCommand(), new AboutCommand());
		getBot().registerEvents();

	}

	public static Bot getBot() {
		return BOT;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
