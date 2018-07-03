package com.agenv.event;

public class AppStateChangedEvent {

	public static AppStateChangedEvent agentTypesChanged = new AppStateChangedEvent();
	public static AppStateChangedEvent runningAgentsChanged = new AppStateChangedEvent();

	public AppStateChangedEvent() {
	}
}
