package services;

import models.Host;

public interface ClusterService {

	void register(Host host);
	void unregister(String alias);
	
}
