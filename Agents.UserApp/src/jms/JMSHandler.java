package jms;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

public class JMSHandler {

	
	@EJB
	private JMSConsumer jmsConsumer;
	
	@PostConstruct
	private void init() {
		
	}
	
	public void handleRequest() {
		
	}
}
