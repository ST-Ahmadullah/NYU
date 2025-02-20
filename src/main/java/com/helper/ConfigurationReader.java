package com.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationReader {

	// Properties to hold configuration data
	private static Properties properties = new Properties();

	// Singleton instance
	private static ConfigurationReader instance;

	// Private constructor to prevent instantiation
	ConfigurationReader() {
		try {
			loadProperties();
		} catch (IOException e) {
			System.err.println("Failed to load properties file: " + e.getMessage());
			throw new RuntimeException("Configuration loading failed", e);
		}
	}

	// Method to get the singleton instance
	public static ConfigurationReader getInstance() {
		if (instance == null) {
			instance = new ConfigurationReader();
		}
		return instance;
	}

	// Method to load properties from the configuration file
	private void loadProperties() throws IOException {
		// Path to the properties file (relative to the classpath)
		String filePath = "src/main/resources/config.properties";

		// Using Path to ensure platform-independent file handling
		Path path = Paths.get(filePath);
		File configFile = path.toFile();

		// Check if the properties file exists
		if (!configFile.exists()) {
			throw new IOException("Properties file not found at: " + filePath);
		}

		// Load properties from the file
		try (InputStream inputStream = new FileInputStream(configFile)) {
			properties.load(inputStream);
		}
	}

	// Generic method to get any property value with a default fallback
	private String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	// Method to get the URL from the properties
	public String getUrl() {
		return getProperty("url", "defaultUrl"); // Provide a default value if needed
	}

	// Method to get the email from the properties
	public String getEmail() {
		return getProperty("email", "defaultEmail"); // Provide a default value if needed
	}

	// Method to get the password from the properties
	public String getPassword() {
		return getProperty("password", "defaultPassword"); // Provide a default value if needed
	}

	// Example of a generic property getter method
	public String getProperty(String key) {
		return getProperty(key, "default"); // If key is not found, return "default"
	}
}