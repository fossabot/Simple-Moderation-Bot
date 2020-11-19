package me.sina.simplebot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class KickCommand extends Command {
	public KickCommand() {
		this.name = "kick";
		this.userPermissions = new Permission[] { Permission.KICK_MEMBERS };
		this.botPermissions = new Permission[] { Permission.KICK_MEMBERS };
		this.help = "Kick a member using this command";
	}

	@Override
	protected void execute(CommandEvent event) {
		final Member member = event.getMember();
		final String[] args = event.getArgs().split("\\s+");

		if (!member.canInteract(event.getSelfMember())) {
			event.reply("You must have a higher role to use this command.");
			return;
		}

		if (args.length < 1) {
			event.reply("Please select a user to get kicked.");
			return;
		}
		final Member toKickMember = MsgHelper.getMember(args[0], event.getGuild());
		if (toKickMember == null) {
			event.reply("Please select a user to get kicked.");
			return;
		}

		if (!event.getSelfMember().canInteract(toKickMember)) {
			event.reply("I cannot kick this member.");
			return;
		}

		String reason = "No Reason Provided";
		if (args.length > 1) {
			reason = event.getArgs().replace(args[0] + " ", "");
		}
		final String finalReason = reason;
		toKickMember.kick(reason).queue(kicked -> {

			event.reply("Successfully kicked this member with reason of: ```" + finalReason + "```");
		}, failure -> {
			event.reply("Something went wrong: " + failure.getLocalizedMessage());
		});

	}

}
