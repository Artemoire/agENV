package services;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import apps.SharedApi;
import cluster.MasterHost;

@Startup
@Singleton
public class StartupService {

	@EJB
	private MasterHost master;

	private String alias;

	@PostConstruct
	void init() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {

		        		if (!master.isHere()) {
		        			Client client = ClientBuilder.newClient();
		        			alias = client.target("http://" + master.getAddress() + "/UserApp/rest" + SharedApi.CLUSTERS)
		        					.request(MediaType.APPLICATION_JSON).post(null).readEntity(String.class);
		        			System.out.println(alias);
		        			// Send Get Logged In Users Request
		        		}
		            }
		        }, 
		        5000 
		);
	}

	@PreDestroy
	void dispose() {
		if (!master.isHere()) {
			Client client = ClientBuilder.newClient();
			client.target("http://" + master.getAddress() + "/UserApp/rest" + SharedApi.CLUSTERS + "/" + alias)
					.request().delete();
		}
	}

}
