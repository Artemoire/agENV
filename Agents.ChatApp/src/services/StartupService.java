package services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class StartupService {

	@PostConstruct
	void init() {
		// TODO
		// If Not Master
		// Send Register Request
		// Send Get Logged In Users Request
	}
	
	@PreDestroy
	void dispose() {
		// TODO		
		// Send Unregister Request
	}

}
