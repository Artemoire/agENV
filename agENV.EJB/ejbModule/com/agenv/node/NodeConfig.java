package com.agenv.node;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ejb.Singleton;

@Singleton
public class NodeConfig {

	private Properties properties;
	private boolean master;

	public void init() throws IOException {
		InputStream inputStream = this.getClass().getClassLoader()

				.getResourceAsStream("cluster.properties");

		properties = new Properties();
		// Loading the properties
		properties.load(inputStream);
		master = masterHost().equals(nodeHost()) && masterAlias().equals(nodeAlias());
	}

	public String masterHost() {
		return properties.getProperty("masterHost");
	}

	public String masterAlias() {
		return properties.getProperty("masterAlias");
	}

	public String nodeHost() {
		return properties.getProperty("nodeHost");
	}

	public String nodeAlias() {
		return properties.getProperty("nodeAlias");
	}

	public boolean isMaster() {
		return master;
	}
}
