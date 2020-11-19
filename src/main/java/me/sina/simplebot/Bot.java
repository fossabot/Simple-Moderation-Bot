package me.sina.simplebot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import me.sina.simplebot.commands.HelpCommand;
import me.sina.simplebot.config.ConfigManager;
import me.sina.simplebot.helpers.MsgHelper;
import net.dv8tion.jda.api.GatewayEncoding;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Bot {

	private final String BOT_INVITE;
	private final EventWaiter WAITER;
	private final JDA JDA;
	private final CommandClient CLIENT;

	public Bot() throws Exception {

		this.JDA = JDABuilder
				.create(GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.GUILD_MESSAGE_REACTIONS)
				.setToken(ConfigManager.getBotToken()).setAutoReconnect(true)
				.setActivity(
						Activity.of(ActivityType.fromKey(ConfigManager.getStatusType()), ConfigManager.getStatusText()))
				.setMemberCachePolicy(MemberCachePolicy.ALL).setChunkingFilter(ChunkingFilter.ALL)
				.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
				.setGatewayEncoding(GatewayEncoding.ETF).build().awaitReady();

		final CommandClientBuilder clientBuilder = new CommandClientBuilder().setHelpWord("help")
				.setHelpConsumer(new HelpCommand().consumer).setPrefix(ConfigManager.getPrefix());
		this.WAITER = new EventWaiter();
		if (!ConfigManager.getOwnerId().isEmpty() && MsgHelper.isLong(ConfigManager.getOwnerId())) {
			clientBuilder.setOwnerId(ConfigManager.getOwnerId());
		} else {

			clientBuilder.setOwnerId(this.getJDA().retrieveApplicationInfo().complete().getOwner().getId());
		}

		this.CLIENT = clientBuilder.build();

		this.getJDA().addEventListener(this.getClient(), this.getWaiter());

		this.BOT_INVITE = this.getJDA().getInviteUrl(Permission.ADMINISTRATOR);
		Main.getLogger().info("Logged in as: " + MsgHelper.getTag(this.getJDA().getSelfUser()));
		Main.getLogger().info("Invite URL: " + this.getInviteUrl());

	}

	public void registerCommands(Command... commands) {
		for (int i = 0; i < commands.length; i++) {
			this.getClient().addCommand(commands[i]);
		}
		Main.getLogger().info("Commands has been loaded");
	}

	public void registerEvents(Object... listeners) {
		for (int i = 0; i < listeners.length; i++) {
			this.getJDA().addEventListener(listeners[i]);
		}
		Main.getLogger().info("Events has been loaded");
	}

	public JDA getJDA() {
		return this.JDA;
	}

	public CommandClient getClient() {
		return this.CLIENT;
	}

	public EventWaiter getWaiter() {
		return this.WAITER;
	}

	public String getInviteUrl() {
		return this.BOT_INVITE;
	}

}