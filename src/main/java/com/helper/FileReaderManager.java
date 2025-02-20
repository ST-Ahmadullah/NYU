package com.helper;

public class FileReaderManager {

	// Singleton instance using Bill Pugh Singleton Design (Thread-safe and
	// efficient)
	private static class Holder {
		private static final FileReaderManager INSTANCE = new FileReaderManager();
	}

	// Instance of ConfigurationReader
	private ConfigurationReader configurationReader;

	// Private Constructor to prevent external instantiation
	private FileReaderManager() {
		// No action needed
	}

	// Static Method to get the Singleton instance of FileReaderManager
	public static FileReaderManager getInstance() {
		return Holder.INSTANCE;
	}

	// Method to get the ConfigurationReader instance (Lazy Initialization)
	public ConfigurationReader getConfigurationReader() {
		// Lazy initialization of ConfigurationReader
		if (configurationReader == null) {
			configurationReader = new ConfigurationReader();
		}
		return configurationReader;
	}
}