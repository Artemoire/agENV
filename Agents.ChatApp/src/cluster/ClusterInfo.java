package cluster;

import models.Host;

public interface ClusterInfo {

	boolean isMaster();
	String getMasterAddress();
	Host getCurrentHost();
}
