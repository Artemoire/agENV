package com.agenv.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.agenv.model.Agent;
import com.agenv.model.Node;
import com.agenv.services.NodeService;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class NodeSyncController {

	@EJB
	private NodeService clusterService;

	@Path("/node")
	@POST
	public void addNodes(List<Node> nodes) {
		clusterService.addNodes(nodes);
	}

	@Path("/agents/running")
	@POST
	public void addAgents(List<Agent> agents) {
		clusterService.addAgents(agents);
	}

}
