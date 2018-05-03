package jms;

import java.io.Serializable;

public class JMSMessage implements Serializable {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
