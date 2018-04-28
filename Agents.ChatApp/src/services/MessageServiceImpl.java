package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import database.MessageRepository;
import models.Message;

@Stateless
public class MessageServiceImpl implements MessageService {

	@EJB
	private MessageRepository messageRepository;
	
	public List<Message> getAll() {		
		List<Message> messages = new ArrayList<Message>();
		messageRepository.getMessages().iterator().forEachRemaining(messages::add);
		return messages;
	}
	
}
