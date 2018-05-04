package jms;

import java.io.Serializable;
import java.util.ArrayList;

public class JMSMessage implements Serializable {

	private String sesId;
	private String methodName;

	private ArrayList<Object> args;

	public JMSMessage(String sesId, String methodName, ArrayList<Object> args) {
		this.methodName = methodName;
		this.args = args;
		this.sesId = sesId;
	}

	public String getMethodName() {
		return methodName;
	}

	public ArrayList<Object> getArgs() {
		return args;
	}

	public String getSesId() {
		return sesId;
	}

}
