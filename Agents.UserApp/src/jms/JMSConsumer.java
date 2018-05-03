//package jms;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.ejb.EJB;
//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageConsumer;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.Session;
//
//import controller.GroupController;
//import controller.UserController;
//import models.User;
//
//public class JMSConsumer implements MessageListener {
//	
//	@Resource(mappedName = "java:/ConnectionFactory")
//    private ConnectionFactory connectionFactory;
//	@Resource(mappedName = "java:/queue/mojQueue")
//    private Queue queue;
//
//	public void acceptMessage(JMSMessage message){
//		
//		delegateMessage(message);		
//	}
//	
//	public void delegateMessage(JMSMessage message){
//	
//		try {
//			Connection connection = connectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageConsumer reciver = session.createConsumer(queue);
//      
//            reciver.setMessageListener(this);
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onMessage(Message arg0) {
//		
//		
//	}
//
//}