package com.almundo.callcenter.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {

	private Properties prop = new Properties();
	private static final Logger log = LogManager.getLogger(Config.class);
	private static Config instance;

	private Config() {
		loadConfig();
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public void loadConfig() {
		log.info("Start loadConfig");
		String propFileName = "config.properties";
		InputStream input = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(input);
			System.out.println(prop.getProperty(ConfigValues.OPERATORS.conf()));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("Finish loadConfig");
	}

	public String getProperty(String key) {
		if (prop != null)
			return prop.getProperty(key);
		return null;
	}

}
