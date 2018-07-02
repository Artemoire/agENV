package com.agenv.services.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.agenv.beans.EnvBean;
import com.agenv.model.AID;
import com.agenv.model.Agent;
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
		} else {
			if (handshakeSlaveService.handshook()) {
				if (nodes.size() != 1)
					return; // Nothing here iether

				envBean.newNodeRegistered(nodes.get(0));
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

}
