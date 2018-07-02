package com.agenv.model;

public class AID {

	private String name;
	private AgentCenter host;
	private AgentType type;

	public AID() {
	}

	public AID(String name, AgentCenter host, AgentType type) {
		this.name = name;
		this.host = host;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentCenter getHost() {
		return host;
	}

	public void setHost(AgentCenter host) {
		this.host = host;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

	// format: name+center+type
	public static AID parse(String aid) {
		String[] splits = aid.split("+");

		if (splits.length != 3)
			throw new RuntimeException("Invalid AID string format");

		return new AID(splits[0], AgentCenter.parse(splits[1]), AgentType.parse(splits[2]));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AID))
			return false;
		AID aid = (AID)obj;
		return aid.name.equals(name) && aid.host.equals(host) && aid.type.equals(type);
	}
}
