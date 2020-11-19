package me.sina.simplebot.helpers;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class MsgHelper {
	public final static Pattern ID = Pattern.compile("\\d{17,20}");
	public final static Pattern MENTION = Pattern.compile("<@!?(\\d{17,20})>");

	public static Member getMember(final String arg, final Guild guild) {
		final Matcher userMention = MENTION.matcher(arg);
		if (userMention.matches()) {
			final Member member = guild.getMemberById(userMention.group(1));
			if (member != null) {
				return member;
			}
		} else if (ID.matcher(arg).matches()) {
			final Member member = guild.getMemberById(arg);
			if (member != null) {
				return member;
			}
		}
		return null;
	}

	public static boolean isLong(final String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (final NumberFormatException err) {
			return false;
		}
	}

	public static EmbedBuilder getEmbedTemplate() {
		final EmbedBuilder eb = new EmbedBuilder();

		eb.setTimestamp(OffsetDateTime.now());

		eb.setColor(Color.GREEN);
		eb.setFooter("Created by Sina");
		return eb;
	}

	public static String formatText(final String text) {
		final String[] strings = text.replaceAll("_", " ").split("\\s+");
		final StringBuilder stringBuilder = new StringBuilder();

		for (final String str : strings) {
			final String firstLetter = str.substring(0, 1);
			final String otherLetters = str.substring(1, str.length());
			stringBuilder.append(firstLetter.toUpperCase() + otherLetters.toLowerCase() + " ");
		}

		final String result = stringBuilder.toString();

		return result.substring(0, result.length() - 1);
	}

	public static String getTag(final User user) {
		final String res = user.getName() + "#" + user.getDiscriminator();
		return res;
	}

	public static String numberFormat(final int value) {
		final String str = String.format("%,d", value);
		return str;
	}

	public static boolean isNumber(final String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (final NumberFormatException err) {
			return false;
		}
	}

	public static String getDate(final OffsetDateTime date) {
		final int day = date.getDayOfMonth();
		final int month = date.getMonthValue();
		final int year = date.getYear();
		final String dateText = day + "-" + month + "-" + year;

		return dateText;
	}

	public static long convertDaysToMilliseconds(final int day) {

		final int hours = day * 24;
		final int minutes = hours * 60;
		final long seconds = minutes * 60;
		final long milliseconds = seconds * 1000;
		return milliseconds;

	}

}
