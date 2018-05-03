package cluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import apps.SharedApi;
import models.Host;

@Startup
@Singleton
public class ClusterLifecycleHook implements ClusterInfo {

	@Resource(name = "MasterNodeHost")
	private String masterAddress;
	private boolean isMaster;
	private Host current;

	@PostConstruct
	void init() {
		isMaster = false;

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {

				if (!isMaster) {
					Client client = ClientBuilder.newClient();
					current = client.target("http://" + masterAddress + "/UserApp/rest" + SharedApi.CLUSTERS)
							.request(MediaType.APPLICATION_JSON).post(null).readEntity(Host.class);
					// Send Get Logged In Users Request
				}
			}
		}, 5000);
	}

	@PreDestroy
	void dispose() {
		if (!isMaster) {
			Client client = ClientBuilder.newClient();
			client.target("http://" + masterAddress + "/UserApp/rest" + SharedApi.CLUSTERS + "/" + current.getAlias())
					.request().delete();
		}
	}

	@Override
	public boolean isMaster() {
		return isMaster;
	}

	@Override
	public String getMasterAddress() {
		return masterAddress;
	}
	
	@Override
	public Host getCurrentHost() {
		return current;
	}

}
