package controllers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import chatapp.ChatApp;
import models.Message;
import services.MessageService;

@Path(ChatApp.Rest.Api.MESSAGE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class MessageController {

	@EJB
	private MessageService messageService;

	// TODO: Accept requests only from other ChatApps
	
	@POST
	public void send(Message message) {
		messageService.forwarded(message);
	}

}
