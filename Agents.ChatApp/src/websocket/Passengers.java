package websocket;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.websocket.Session;

@Stateless
public class Passengers {

	private
	HashMap<String, Session> passerbies;
	
	@PostConstruct
	private void init() {
		passerbies = new HashMap<>();
	}
	
	public Session get(String sesId) {
		return passerbies.get(sesId);
	}
	
	public Session pop(String sesId) {
		Session ses = passerbies.get(sesId);
		passerbies.remove(ses);
		return ses;
	}
	
	public void push(Session ses) {
		passerbies.put(ses.getId(), ses);
	}
	
	
}
