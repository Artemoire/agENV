package mdb;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jms.JMSMethodNames;
import jms.JMSResponse;
import websocket.Passengers;
import websocket.WSError;
import websocket.WSUPMessage;
import websocket.WSUPResponseType;
import websocket.context.WSUPContext;
import websocket.services.WSResponseUserService;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue") })
public class MDBConsumer implements MessageListener {

	private HashMap<String, Consumer<WSUPContext>> responseConsumerMap;
	
	@EJB
	private WSResponseUserService userService;
	
	@EJB
	private Passengers passengers;
	
	@PostConstruct
	private void init() {
		responseConsumerMap = new HashMap<>();
		responseConsumerMap.put(JMSMethodNames.login, userService::register);
		responseConsumerMap.put(JMSMethodNames.register, userService::register);
		responseConsumerMap.put(JMSMethodNames.getFriends, userService::getFriends);
		responseConsumerMap.put(JMSMethodNames.getAllUsers, userService::getAll);
	}
	
	@Override
	public void onMessage(Message arg0) {
		System.out.println("MDB CHATAPP CONSUMED");
		JMSResponse response;
		try {
			response= (JMSResponse)((ObjectMessage) arg0).getObject();
			WSUPMessage message;
			if (response.getStatus() == 200) {
				message = WSUPMessage.buildResponse(WSUPResponseType.SUCCESS, response.getMethodName(), response.getEntity());
			} else {
				 message = WSUPMessage.buildResponse(WSUPResponseType.FAILURE, response.getMethodName(), new WSError(response.getStatus(), response.getEntity().toString()));
			}
			if (message == null) // todo: remove
				return;
			WSUPContext context = new WSUPContext(message, passengers.get(response.getSesId()));
			responseConsumerMap.get(response.getMethodName()).accept(context);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
