package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jms.JMSMessage;
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName= "destinationType",propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName= "destination",propertyValue = "jms/queue/mojQueue")
})
public class MDBConsumer implements MessageListener{

	@Override
	public void onMessage(Message arg0) {

		Object object;
		try {
			object = ((ObjectMessage)arg0).getObject();
			JMSMessage message = (JMSMessage)object;
			System.out.println(message.getData());
				
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}