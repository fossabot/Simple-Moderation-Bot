package me.sina.simplebot.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.file.YamlConfiguration;

public class ConfigFile {

	File file;
	YamlConfiguration ymlconfig;

	public ConfigFile() {
		this.file = new File("./Config.txt");
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (final IOException err) {
				err.printStackTrace();
			}
		}

		this.ymlconfig = YamlConfiguration.loadConfiguration(this.file);
		this.setup();
	}

	public void setup() {

		if (!this.contains("PREFIX")) {
			this.set("PREFIX", "-");
		}

	}

	public Boolean contains(final String str) {
		if (this.ymlconfig.contains(str)) {
			return true;
		}
		return false;
	}

	public Boolean getBoolean(final String str) {
		if (this.ymlconfig.contains(str)) {
			return this.ymlconfig.getBoolean(str);
		}
		return null;
	}

	public YamlConfiguration getConfiguration() {
		return this.ymlconfig;
	}

	public ConfigurationSection getConfigurationSection(final String str) {
		if (this.ymlconfig.contains(str)) {
			return this.ymlconfig.getConfigurationSection(str);
		}
		return null;
	}

	public File getFile() {
		return this.file;
	}

	public int getInt(final String str) {
		if (this.ymlconfig.contains(str)) {
			return this.ymlconfig.getInt(str);
		}
		return 0;
	}

	public String getString(final String str) {
		if (this.ymlconfig.contains(str)) {
			return this.ymlconfig.getString(str);
		}
		return null;
	}

	public List<String> getStringList(final String str) {
		if (this.ymlconfig.contains(str)) {
			return this.ymlconfig.getStringList(str);
		}
		return null;
	}

	public void load() {
		this.file = new File("./Config.txt");
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (final IOException err) {
				err.printStackTrace();
			}
		}
		this.ymlconfig = YamlConfiguration.loadConfiguration(this.file);
		this.setup();
	}

	public ConfigurationSection set(final String string, final Object obj) {
		this.ymlconfig.set(string, obj);
		try {
			this.ymlconfig.save(this.file);
		} catch (final Exception err) {
			err.printStackTrace();
		}
		return this.ymlconfig;
	}

}
