package mdb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import controller.GroupController;
import controller.UserController;
import jms.JMSMessage;
import jms.JMSMethodResolver;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue") })
public class MDBConsumer implements MessageListener {

	private HashMap<Class<?>, Object> instances = new HashMap<>();

	@EJB
	GroupController groupController;

	@EJB
	UserController userController;

	@EJB
	JMSMethodResolver jmsMethodResolver;

	@PostConstruct
	private void init() {
		instances.put(groupController.getClass(), groupController);
		instances.put(userController.getClass(), userController);
	}

	@Override
	public void onMessage(Message arg0) {

		Object object;
		try {
			object = ((ObjectMessage) arg0).getObject();
			JMSMessage message = (JMSMessage) object;
			Method m = jmsMethodResolver.resolve(message.getMethodName());
			m.invoke(instances.get(m.getDeclaringClass()), message.getArgs().toArray());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}