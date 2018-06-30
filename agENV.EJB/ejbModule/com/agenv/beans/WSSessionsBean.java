package com.agenv.beans;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.websocket.Session;

@Singleton
public class WSSessionsBean {

	private Set<Session> sessions;

	@PostConstruct
	private void init() {
		sessions = new LinkedHashSet<>();
	}

	public void open(Session session) {
		sessions.add(session);
	}

	public void close(Session session) {
		sessions.remove(session);
	}

	public Stream<Session> stream() {
		return sessions.stream();
	}
}
