package services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import controllers.UserController;
import models.User;

@Startup
@Singleton
public class StartupService {

	@PostConstruct
	void init() {
		// TODO
		// If Not Master
		// Send Register Request
		// Send Get Logged In Users Request
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				Client c = ClientBuilder.newClient();
				String s = c.target("http://localhost:8080/ChatApp/rest/users").request(MediaType.APPLICATION_JSON)
						.get(String.class);
			}
		}, 5000);
	}

	@PreDestroy
	void dispose() {
		// TODO
		// Send Unregister Request
	}

}
