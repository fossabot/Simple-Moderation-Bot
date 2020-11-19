package me.sina.simplebot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import me.sina.simplebot.Main;
import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class AboutCommand extends Command {
	public AboutCommand() {
		this.name = "about";
		this.aliases = new String[] { "bot", "botinfo" };
		this.help = "Get some information about me";
	}

	@Override
	protected void execute(CommandEvent event) {

		final Member member = event.getSelfMember();

		final EmbedBuilder embed = MsgHelper.getEmbedTemplate();
		embed.setAuthor(MsgHelper.getTag(member.getUser()), null, member.getUser().getEffectiveAvatarUrl());
		embed.addField("Bot Name", MsgHelper.getTag(member.getUser()), false);
		embed.addField("ID", member.getId(), false);
		embed.addField("Creation Date", MsgHelper.getDate(member.getTimeCreated()), false);
		embed.addField("Added Date", MsgHelper.getDate(member.getTimeJoined()), false);
		embed.addField("Servers", MsgHelper.numberFormat(Main.getBot().getJDA().getGuilds().size()), false);
		embed.addField("Users", MsgHelper.numberFormat(Main.getBot().getJDA().getUsers().size()), false);
		embed.addField("Gateway Ping", String.valueOf(Main.getBot().getJDA().getGatewayPing()), false);
		event.reply(embed.build());
	}

}
