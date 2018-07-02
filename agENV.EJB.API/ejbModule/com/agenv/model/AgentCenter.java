package com.agenv.model;

public class AgentCenter {

	private String alias;

	private String address;

	public AgentCenter() {
	}

	public AgentCenter(String alias, String address) {
		this.alias = alias;
		this.address = address;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// format: alias;address
	public static AgentCenter parse(String type) {
		String[] splits = type.split(";");
		if (splits.length != 2)
			throw new RuntimeException("Invalid AgentCenter string format");
		return new AgentCenter(splits[0], splits[1]);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AgentCenter))
			return false;
		AgentCenter agentCenter = (AgentCenter)obj;
		return agentCenter.address.equals(address) && agentCenter.alias.equals(alias);
	}

}
