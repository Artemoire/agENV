package jms;

import java.io.Serializable;

public class JMSResponse implements Serializable {

	private int status;
	private Object entity;
	private String methodName;
	private String userId;
	
	public JMSResponse(int status, Object entity) {
		super();
		this.status = status;
		this.entity = entity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}