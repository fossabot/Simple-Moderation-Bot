package me.sina.simplebot.commands;

import java.util.function.Consumer;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import me.sina.simplebot.Bot;
import me.sina.simplebot.Main;
import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.EmbedBuilder;

public class HelpCommand {

	public Consumer<CommandEvent> consumer = event -> {
		if (!event.getChannelType().isGuild()) {
			return;
		}
		final StringBuilder commands = new StringBuilder();
		final Bot bot = Main.getBot();
		for (final Command command : bot.getClient().getCommands()) {
			commands.append(
					"**" + bot.getClient().getPrefix() + command.getName() + "**\n  `" + command.getHelp() + "`\n\n");
		}

		final EmbedBuilder embed = MsgHelper.getEmbedTemplate();
		embed.setTitle("Commands List");
		embed.setDescription(commands.toString());
		event.reply(embed.build());

	};

}
