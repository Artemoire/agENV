package com.agenv.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.agenv.model.ACLMessage;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;
import com.agenv.services.AgentService;
import com.agenv.services.InitService;
import com.agenv.services.MessageService;

@LocalBean
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class ClientAgentCenter {

	@EJB
	InitService init;

	@EJB
	AgentService agentService;

	@EJB
	MessageService messageService;

	@GET
	@Path("/agents/classes")	
	public List<AgentType> getTypes() {
		return agentService.getAllTypes();
	}

	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Agent> getAgents() {
		return agentService.getRunningAgents();
	}

	@PUT
	@Path("/agents/running/{type}/{name}")
	public void startAgent(@PathParam("type") String type, @PathParam("name") String name) {
		agentService.startAgent(type, name);

	}

	@DELETE
	@Path("/agents/running/{aid}")
	public void stopAgent(@PathParam("aid") String aid) {
		agentService.stopAgent(aid);
	}
	
	@POST
	@Path("/messages")
	public void sendMessage(ACLMessage message) {
		messageService.sendACLMessage(message);
	}
	
	@GET
	@Path("/messages")
	public List<String> getPerformative() {
		return messageService.getPerformatives();
	}
}
