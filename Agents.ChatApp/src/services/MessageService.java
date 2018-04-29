package services;

import models.Message;

public interface MessageService {

	void forwarded(Message message);
	void send(Message message);
	
}
