package services;

import java.util.HashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import database.MessageRepository;
import models.Host;
import models.Message;

@Stateless
public class MessageServiceImpl implements MessageService {

	@EJB
	private MessageRepository messageRepository;

	@Override
	public void forwarded(Message message) {
		if (message.getGroupId() != null) {
			forwardedGroup(message);
			return;
		}
		// Find User Session, send message.
		// if master save
	}
	
	private void forwardedGroup(Message message) {
		// Find group
		// For each userId get Session
		// send message
		// if master save
	}
	
	public void send(Message message) {
		if (message.getGroupId() != null) {
			sendGroup(message);
			return;
		}
		// Find reciever's Host
		// if host != thisHost
		// rest post message object
		// else websock send
		// if master save
		// else if host != master
		// rest post message object
	}
	
	private void sendGroup(Message message) {
		HashSet<Host> sentTo = new HashSet<Host>();
		// Find group
		// For each userId get Host
		// if host != thisHost
		// if host isnt inside sentTo
		// add
		// rest post message object
		// else websock send
		// if master save
		// else if !sentTo.contains(master) rest post message object
	}
		
}
