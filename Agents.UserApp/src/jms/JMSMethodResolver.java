package jms;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import controller.GroupController;
import controller.UserController;
import models.User;

@Stateless
public class JMSMethodResolver {

	private HashMap<String, Method> methods = new HashMap<String, Method>();

	@EJB
	GroupController groupController;

	@EJB
	UserController userController;
	
	@PostConstruct
	public void init() {
		try {
			methods.put(JMSMethodNames.register, userController.getClass().getMethod("registerUser", User.class));
			methods.put(JMSMethodNames.login, userController.getClass().getMethod("login", User.class));
			methods.put(JMSMethodNames.logout, userController.getClass().getMethod("logout", User.class));
			methods.put(JMSMethodNames.addFriend,
					userController.getClass().getMethod("addFriend", String.class, String.class));
			methods.put(JMSMethodNames.deleteFriend,
					userController.getClass().getMethod("deleteFriend", String.class, String.class));
			methods.put(JMSMethodNames.activeUsers, userController.getClass().getMethod("activeUsers", (Class<?>[]) null));
			methods.put(JMSMethodNames.createGroup,
					groupController.getClass().getMethod("createGroup", String.class, String.class));
			methods.put(JMSMethodNames.deleteGroup,
					groupController.getClass().getMethod("deleteGroup", String.class, String.class));
			methods.put(JMSMethodNames.addNewUserGroup,
					groupController.getClass().getMethod("addNewUser", String.class, String.class, String.class));
			methods.put(JMSMethodNames.removeUserGroup,
					groupController.getClass().getMethod("deleteUser", String.class, String.class, String.class));

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Method resolve(String methodName) {
	
		return methods.get(methodName);
	}

}
