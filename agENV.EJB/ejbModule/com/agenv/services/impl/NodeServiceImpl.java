package com.agenv.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Node;
import com.agenv.node.NodeConfig;
import com.agenv.services.HandshakeMasterService;
import com.agenv.services.HandshakeSlaveService;
import com.agenv.services.NodeService;

@Stateless
public class NodeServiceImpl implements NodeService {

	@EJB
	private NodeConfig nodeConfig;

	@EJB
	private HandshakeSlaveService handshakeSlaveService;

	@EJB
	private HandshakeMasterService handshakeMasterService;

	@EJB
	private EnvBean envBean;

	@Override
	public void addNodes(List<Node> nodes) {
		if (nodeConfig.isMaster()) {
			if (nodes.size() != 1)
				return; // No need to do anything here
			handshakeMasterService.registerNode(nodes.get(0));
			// TODO: Refresh front types
		} else {
			if (handshakeSlaveService.handshook()) {
				if (nodes.size() != 1)
					return; // Nothing here iether

				envBean.newNodeRegistered(nodes.get(0));
				// TODO: Refresh front types
			} else {
				handshakeSlaveService.receiveNodes(nodes);
			}
		}
	}

	@Override
	public void addAgents(List<AID> agents) {
		if (handshakeSlaveService.handshook() || nodeConfig.isMaster()) {
			if (agents.size() != 1)
				return; // nothing to do here

			envBean.addNewAgent(agents.get(0));
		} else {
			handshakeSlaveService.receiveAgents(agents);
		}
	}

	@Override
	public void removeNode(String alias) {
		if (nodeConfig.isMaster())
			for (Node node : envBean.getNodes())
				ClientBuilder.newClient()
						.target("http://" + node.getCenter().getAddress() + "/agENV/rest/node/" + alias).request()
						.async().delete();

		envBean.removeNodeByAlias(alias);
	}

	@Override
	public void deleteAgent(AID agent) {
		if (handshakeSlaveService.handshook() || nodeConfig.isMaster()) {
			envBean.removeAgent(agent);
		} else {

		}
	}

	@Override
	public Node getNode() {
		return envBean.getLocalNode();
	}

}
