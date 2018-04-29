package cluster;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;

import models.Host;

@Singleton
public class MasterHost extends Host {

	@Resource(name = "MasterNodeHost")
	private String masterNodeAddress;

	private boolean isHere;

	@PostConstruct
	private void init() {
		setAddress(masterNodeAddress);
		setAlias("master");
		isHere = masterNodeAddress.startsWith("localhost");
	}
	
	public boolean isHere() {
		return isHere;
	}

}
