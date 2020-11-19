package me.sina.simplebot.config;

import net.dv8tion.jda.api.entities.Activity.ActivityType;

public class ConfigManager {

	private static String OWNER_ID;
	private static String TOKEN;
	private static String PREFIX;
	private static String STATUS_TEXT;
	private static int STATUS_TYPE;

	public static void update() {
		final ConfigFile file = new ConfigFile();
		OWNER_ID = file.getString("OWNER_ID");
		TOKEN = file.getString("TOKEN");
		PREFIX = file.getString("PREFIX");
		if (PREFIX.isBlank()) {
			PREFIX = "-";
		}
		STATUS_TEXT = file.getString("STATUS_TEXT");
		STATUS_TYPE = file.getInt("STATUS_TYPE");
		try {
			ActivityType.fromKey(STATUS_TYPE);
		} catch (final IllegalArgumentException err) {
			STATUS_TYPE = ActivityType.DEFAULT.getKey();
		}
		if (STATUS_TEXT.isEmpty()) {
			STATUS_TEXT = getPrefix() + "Help";
		}
	}

	public static String getBotToken() {
		return TOKEN;
	}

	public static String getOwnerId() {
		return OWNER_ID;
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static int getStatusType() {
		return STATUS_TYPE;
	}

	public static String getStatusText() {
		return STATUS_TEXT;
	}

}
