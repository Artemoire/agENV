package services;

import java.util.HashMap;

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
import models.Host;

@Startup
@Singleton
public class StartupService {

	@EJB
	private MasterHost master;

	private String alias;
	
	private String masterAddress;
	private boolean isMaster;

	@PostConstruct
	void init() {
		isMaster = master.isHere();
		masterAddress = master.getAddress();
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {

		        		if (!master.isHere()) {
		        			Client client = ClientBuilder.newClient();
		        			alias = client.target("http://" + master.getAddress() + "/UserApp/rest" + SharedApi.CLUSTERS)
		        					.request(MediaType.APPLICATION_JSON).post(null).readEntity(String.class);		        			
		        			// Send Get Logged In Users Request
		        		}
		            }
		        }, 
		        5000 
		);
	}

	@PreDestroy
	void dispose() {
		if (!isMaster) {
			Client client = ClientBuilder.newClient();
			client.target("http://" + masterAddress + "/UserApp/rest" + SharedApi.CLUSTERS + "/" + alias)
					.request().delete();
		}
	}

}
