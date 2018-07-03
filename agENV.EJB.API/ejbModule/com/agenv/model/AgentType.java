package com.agenv.model;

import java.io.Serializable;

public class AgentType implements Serializable {

	private String name;
	private String module;

	public AgentType() {
	}

	public AgentType(String name, String module) {
		this.name = name;
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	// format: name;module
	public static AgentType parse(String type) {
		String[] splits = type.split(";");
		if (splits.length != 2)
			throw new RuntimeException("Invalid AgentType string format");
		return new AgentType(splits[0], splits[1]);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AgentType))
			return false;
		AgentType agentType = (AgentType) obj;
		return agentType.name.equals(name) && agentType.module.equals(module);
	}
}
