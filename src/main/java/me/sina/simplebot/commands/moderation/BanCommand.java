package me.sina.simplebot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class BanCommand extends Command {
	public BanCommand() {
		this.name = "ban";
		this.userPermissions = new Permission[] { Permission.BAN_MEMBERS };
		this.botPermissions = new Permission[] { Permission.BAN_MEMBERS };
		this.help = "Ban a member using this command";
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
			event.reply("Please select a user to get banned.");
			return;
		}
		final Member toBanMember = MsgHelper.getMember(args[0], event.getGuild());
		if (toBanMember == null) {
			event.reply("Please select a user to get banned.");
			return;
		}

		if (!event.getSelfMember().canInteract(toBanMember)) {
			event.reply("I cannot ban this member.");
			return;
		}

		String reason = "No Reason Provided";
		if (args.length > 1) {
			reason = event.getArgs().replace(args[0] + " ", "");
		}
		final String finalReason = reason;
		toBanMember.ban(0, reason).queue(banned -> {

			event.reply("Successfully banned this member with reason of: ```" + finalReason + "```");
		}, failure -> {
			event.reply("Something went wrong: " + failure.getLocalizedMessage());
		});

	}

}
