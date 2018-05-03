package jms;

import java.io.Serializable;
import java.util.ArrayList;

public class JMSMessage implements Serializable {

	private String methodName;

	private ArrayList<Object> args;

	public JMSMessage(String methodName, ArrayList<Object> args) {
		this.methodName = methodName;
		this.args = args;
	}

	public String getMethodName() {
		return methodName;
	}

	public ArrayList<Object> getArgs() {
		return args;
	}

}
