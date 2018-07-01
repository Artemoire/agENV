package com.agenv.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.agenv.init.InitAgents;
import com.agenv.model.Agent;
import com.agenv.model.AgentType;

@LocalBean
@Path("/agentCenter")
@Stateless
public class ClientAgentCenter {

	@EJB
	InitAgents init;

	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getTipovi() {
		return init.getTypes();
	}
	
	@POST
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public void createTypes(List<AgentType> agentTypes) {
		init.insertAgentType(agentTypes);
	}
	
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Agent> getAgents() {return new ArrayList<>(init.getAgents().values());}
	
	
	
	

}
