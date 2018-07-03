package com.agenv.event;

public interface ApplicationMessenger {

	void log(String message);
	void fireAgentsChanged();
	void fireAgentTypesChanged();
	
}
