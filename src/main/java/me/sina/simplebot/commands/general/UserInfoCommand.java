package me.sina.simplebot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class UserInfoCommand extends Command {
	public UserInfoCommand() {
		this.name = "user";
		this.aliases = new String[] { "userinfo" };
		this.help = "Check your or other users information";
	}

	@Override
	protected void execute(CommandEvent event) {
		final String[] args = event.getArgs().split("\\s+");

		Member member = event.getMember();
		if (MsgHelper.getMember(args[0], event.getGuild()) != null) {
			member = MsgHelper.getMember(args[0], event.getGuild());
		}

		final EmbedBuilder embed = MsgHelper.getEmbedTemplate();
		embed.setAuthor(MsgHelper.getTag(member.getUser()), null, member.getUser().getEffectiveAvatarUrl());
		embed.addField("Username", MsgHelper.getTag(member.getUser()), false);
		embed.addField("ID", member.getId(), false);
		embed.addField("Creation Date", MsgHelper.getDate(member.getTimeCreated()), false);
		embed.addField("Join Date", MsgHelper.getDate(member.getTimeJoined()), false);

		String memberRoles = "Empty";
		if (!member.getRoles().isEmpty()) {
			final StringBuilder roles = new StringBuilder();
			member.getRoles().forEach(role -> {
				roles.append(role.getAsMention() + ", ");
			});
			memberRoles = roles.substring(0, roles.length() - 2);
		}
		embed.addField("Roles", memberRoles, false);
		event.reply(embed.build());
	}

}
